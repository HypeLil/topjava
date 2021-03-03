package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final ArrayList<Meal> repository = new ArrayList();
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
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
        log.info("save {}", meal);
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
        log.info("delete {}", id);
        if (repository.get(id-1).getUserId() != userId){
            return false;
        }
        return repository.remove(id-1) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        if (repository.get(id-1).getUserId() != userId) {
        return null;
        }
        log.info("get {}", id);
        return repository.get(id-1);
    }

    @Override
    public List<Meal> getAll() {
        log.info("getAll");
        if (repository.get(repository.size()-1).getDate().isBefore(repository.get(0).getDate())) {
            repository.sort(Comparator.comparing(Meal::getDate));
            Collections.reverse(repository);
        }
        return repository;
    }
}

