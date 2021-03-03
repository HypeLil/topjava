package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMealRepository implements MealRepository {
    private final CopyOnWriteArrayList<Meal> repository = new CopyOnWriteArrayList();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(m -> {
            save(m, m.getUserId());
        });
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.getUserId() != userId) {
           return null;
        }
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.add(meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        if (repository.get(id).getUserId() != userId){
            return false;
        }
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        if (repository.get(id).getUserId() != userId) {
        return null;
        }
        return repository.get(id);
    }

    @Override
    public List<Meal> getAll() {
        repository.sort(Comparator.comparing(Meal::getDate));
        Collections.reverse(repository);
        return repository;
    }
}

