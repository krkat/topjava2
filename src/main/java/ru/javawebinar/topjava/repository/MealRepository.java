package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface MealRepository {
    Meal get(int id);

    Collection<Meal> getAll();

    Meal save(Meal meal);

    boolean delete(int id);
}
