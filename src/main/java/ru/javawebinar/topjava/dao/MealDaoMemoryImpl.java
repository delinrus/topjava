package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

public class MealDaoMemoryImpl implements MealDao {
    private static final long NO_ID = 0L;
    private static MealDaoMemoryImpl instance;
    private final ConcurrentHashMap<Long, Meal> mealsMap = new ConcurrentHashMap<>();
    private AtomicLong idCounter = new AtomicLong();

    {
        create(new Meal(NO_ID, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        create(new Meal(NO_ID, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        create(new Meal(NO_ID, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        create(new Meal(NO_ID, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        create(new Meal(NO_ID, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        create(new Meal(NO_ID, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        create(new Meal(NO_ID,  LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    private MealDaoMemoryImpl() {
    }

    public static MealDaoMemoryImpl getInstance() {
        return instance = (instance == null) ? new MealDaoMemoryImpl() : instance;
    }

    @Override
    public Meal get(Long id) {
        return mealsMap.get(id);
    }

    @Override
    public Meal create(Meal meal) {
        Long id = idCounter.incrementAndGet();
        mealsMap.put(id, new Meal(id, meal));
        return meal;
    }

    @Override
    public Meal update(Meal meal) {
        if (mealsMap.replace(meal.getId(), meal) == null) {
            return null;
        }
        return meal;
    }

    @Override
    public void delete(Long id) {
        mealsMap.remove(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealsMap.values());
    }
}