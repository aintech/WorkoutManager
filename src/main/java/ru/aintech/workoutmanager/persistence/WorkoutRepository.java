package ru.aintech.workoutmanager.persistence;

import java.util.List;

/**
 *
 * @author ainte
 */
public interface WorkoutRepository {
    Workout getWorkout (int id);
    List<Workout> getAll ();
}
