package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {
    Meal get(Long id);
    void create(Meal meal);
    void update(Meal meal);
    void delete(Long id);
    List<Meal> getAll();
}
