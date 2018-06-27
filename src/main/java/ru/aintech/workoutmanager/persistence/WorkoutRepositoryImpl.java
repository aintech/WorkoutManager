package ru.aintech.workoutmanager.persistence;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Yaremchuk E.N. (aka Aintech)
 */

@Repository
@Transactional
public class WorkoutRepositoryImpl implements WorkoutRepository {
    
    private final SessionFactory sessionFactory;

    @Autowired
    public WorkoutRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    private Session getCurrentSession () {
        return sessionFactory.getCurrentSession();
    }
    
    @Override
    public Workout save(Workout workout) {
        Serializable id = getCurrentSession().save(workout);
        return new Workout(((Workout)id).getId(), workout.getName(), workout.getExercises());
    }
    
    @Override
    public Workout getWorkout(int id) {
        return PersistenceManager.getInstance().getWorkouts().stream().filter(workout -> workout.getId() == id).collect(Collectors.toList()).get(0);
    }
    
    @Override
    public List<Workout> getAll () {
        return PersistenceManager.getInstance().getWorkouts();
    }
}