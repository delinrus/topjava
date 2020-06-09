package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public class MealDaoMemoryImpl implements MealDao {
    private static MealDaoMemoryImpl instance;
    private final ConcurrentHashMap<Long, Meal> mealsMap = new ConcurrentHashMap<>();

    {
        mapInitHelper(2L, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
        mapInitHelper(5L, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
        mapInitHelper(6L, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
        mapInitHelper(7L, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
        mapInitHelper(8L, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
        mapInitHelper(9L, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
        mapInitHelper(10L, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
    }

    private MealDaoMemoryImpl() {
    }

    public static MealDaoMemoryImpl getInstance() {
        return instance = (instance == null) ? new MealDaoMemoryImpl() : instance;
    }

    private void mapInitHelper(Long id, LocalDateTime dateTime, String description, int calories) {
        mealsMap.put(id, new Meal(id, dateTime, description, calories));
    }

    @Override
    public Meal get(Long id) {
        return mealsMap.get(id);
    }

    @Override
    public void create(Meal meal) {
        Long id = meal.getId();
        while (mealsMap.containsKey(id)) {
            id = ThreadLocalRandom.current().nextLong(1, Long.MAX_VALUE);
        }
        mealsMap.put(id, new Meal(id, meal));
    }

    @Override
    public void update(Meal meal) {
        mealsMap.replace(meal.getId(), meal);
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