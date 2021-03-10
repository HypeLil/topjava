package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.getUser().getId() != userId){
            return null;
        }
        if (meal.isNew()){
            em.persist(meal);
            return meal;
        }
        else {
            return em.merge(meal);
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        Meal meal = em.getReference(Meal.class, userId);
        if (meal.getUser().getId() != userId){
            return false;
        }
        Query query = em.createQuery("DELETE FROM Meal m WHERE m.id=:id");
        return query.setParameter("id", id).executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = em.find(Meal.class, id);
        if (meal.getUser().getId() == userId){
            return meal;
        }
        return null;
    }

    @Override
    public List<Meal> getAll(int userId){
        Query query = em.createQuery("SELECT m FROM Meal m WHERE m.user.id=:userId ORDER BY m.dateTime DESC");
        return query.setParameter("userId", userId).getResultList();
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        Query query = em.createQuery("SELECT m FROM Meal m WHERE m.user.id=:userId and " +
                "m.dateTime>=:start and m.dateTime<=:end");
        return query.setParameter("userId", userId).setParameter("start",startDateTime)
                .setParameter("end",endDateTime).getResultList();
    }
}