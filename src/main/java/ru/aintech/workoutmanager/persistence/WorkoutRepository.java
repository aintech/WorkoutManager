package ru.aintech.workoutmanager.persistence;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 *
 * @author Yaremchuk E.N. (aka Aintech)
 */

@Component
public class WorkoutRepository implements IWorkoutRepository {

    @Override
    public Workout getWorkout(int id) {
        return PersistenceRetriever.getInstance().getWorkouts().stream().filter(workout -> workout.getId() == id).collect(Collectors.toList()).get(0);
    }
    
    @Override
    public List<Workout> getAll () {
        return PersistenceRetriever.getInstance().getWorkouts();
    }
}