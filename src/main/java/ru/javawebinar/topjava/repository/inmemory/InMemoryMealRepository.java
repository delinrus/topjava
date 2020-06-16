package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>(); // Map <userId, Map<mealId, Meal>>
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(m -> this.save(m.getUserId(), m));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        if (meal.getUserId() != userId) {
            return null;
        }
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.computeIfAbsent(userId, ConcurrentHashMap::new).put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        if (get(meal.getUserId(), meal.getId()) == null) {
            return null;
        }

        return repository.get(userId).computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int userId, int id) {
        if (get(userId, id) != null) {
            return repository.get(userId).remove(id) != null;
        }
        return false;
    }

    @Override
    public Meal get(int userId, int id) {
        Meal meal = null;
        if (repository.containsKey(userId)) {
            meal = repository.get(userId).get(id);
        }
        if (meal != null && meal.getUserId() == userId) {
            return meal;
        }
        return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getFilteredByPredicate(userId, m -> true);
    }

    @Override
    public List<Meal> getFilteredByDates(int userId, LocalDate fromDate, LocalDate toDate) {
        Predicate<Meal> datePredicate = m -> true;
        if (fromDate != null) {
            datePredicate = datePredicate.and(m -> m.getDate().compareTo(fromDate) >= 0);
        }
        if (toDate != null) {
            datePredicate = datePredicate.and(m -> m.getDate().compareTo(toDate) <= 0);
        }
        return getFilteredByPredicate(userId, datePredicate);
    }

    private List<Meal> getFilteredByPredicate(int userId, Predicate<Meal> predicate) {
        if (!repository.containsKey(userId)) {
            return new ArrayList<>();
        }
        return repository.get(userId).values().stream()
                .filter(predicate)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

