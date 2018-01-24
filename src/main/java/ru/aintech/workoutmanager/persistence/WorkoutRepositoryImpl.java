package ru.aintech.workoutmanager.persistence;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 *
 * @author Yaremchuk E.N. (aka Aintech)
 */

@Component
public class WorkoutRepositoryImpl implements WorkoutRepository {

    @Override
    public Workout getWorkout(int id) {
        return PersistenceManager.getInstance().getWorkouts().stream().filter(workout -> workout.getId() == id).collect(Collectors.toList()).get(0);
    }
    
    @Override
    public List<Workout> getAll () {
        return PersistenceManager.getInstance().getWorkouts();
    }
}