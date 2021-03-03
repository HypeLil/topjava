package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithIdAndOwnByUserId;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(@Qualifier("inMemoryMealRepository") MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, int userId){
        return repository.save(meal, userId);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithIdAndOwnByUserId(repository.delete(id, userId), id, userId);
    }

    public Meal get(int id, int userId) {
        return checkNotFoundWithIdAndOwnByUserId(repository.get(id, userId), id, userId);
    }

    public List<Meal> getAll() {
        return repository.getAll();
    }

    public void update(Meal meal, int userId) {
        checkNotFoundWithIdAndOwnByUserId(repository.save(meal, userId),meal.getId(), userId);
    }

}