package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryMealRepository implements MealRepository{

    private Map<Integer, Meal> repository = new HashMap<>();

    @Override
    public Meal findById(int id) {
        return repository.get(id);
    }

    @Override
    public List<Meal> findAll() {
        List<Meal> meals = new ArrayList<>();
        meals.addAll(repository.values());
        return meals;
    }

    @Override
    public void add(Meal meal) {
        meal.setId();
        repository.put(meal.getId(), meal);
    }

    @Override
    public void update(int mealId, Meal meal) {
        repository.put(mealId, meal);
    }

    @Override
    public void delete(int id) {
        repository.remove(id);
    }
}