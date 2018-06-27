package ru.aintech.workoutmanager.persistence;

import java.util.List;

/**
 *
 * @author ainte
 */
public interface WorkoutRepository {
    Workout save (Workout workout);
    Workout getWorkout (int id);
    List<Workout> getAll ();
}
