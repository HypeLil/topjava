package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.util.List;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    private final MealService service;

    public MealRestController(@Qualifier("mealService") MealService service) {
        this.service = service;
    }

    public Meal create(Meal meal){
        return service.create(meal, authUserId());
    }

    public void delete(int id) {
        service.delete(id, authUserId());
    }

    public Meal get(int id) {
        return service.get(id, authUserId());
    }

    public List<Meal> getAll() {
        return service.getAll();
    }

    public void update(Meal meal) {
        service.update(meal, authUserId());
    }
}