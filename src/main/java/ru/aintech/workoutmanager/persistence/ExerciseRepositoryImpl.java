package ru.aintech.workoutmanager.persistence;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Yaremchuk E.N. (aka Aintech)
 */

@Component
public class ExerciseRepositoryImpl implements ExerciseRepository {
    
    private final SessionFactory sessionFactory;
    
    @Autowired
    public ExerciseRepositoryImpl (SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    private Session getCurrentSession () {
        return sessionFactory.getCurrentSession();
    }
    
    @Override
    public Exercise getExercise(int id) {
        return getCurrentSession().get(Exercise.class, id);
    }
    
    public List<Exercise> getExercisesForWorkout (int workoutId) {
        return getCurrentSession().createQuery("from " + ExerciseWorkoutBinding.class.getName() + " where workout.id = " + workoutId).list();
    }
}