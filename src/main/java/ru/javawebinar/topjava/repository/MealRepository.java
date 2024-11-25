package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealRepository {
    Meal findById(int id);

    List<Meal> findAll();

    void add(Meal meal);

    void update(int mealId, Meal meal);

    void delete(int id);
}
