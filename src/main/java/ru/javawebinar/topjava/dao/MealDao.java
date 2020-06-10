package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {
    Meal get(Long id);
    Meal create(Meal meal);
    Meal update(Meal meal);
    void delete(Long id);
    List<Meal> getAll();
}
