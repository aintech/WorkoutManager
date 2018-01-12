package ru.aintech.workoutmanager.persistence;

import java.util.List;

/**
 *
 * @author ainte
 */
public interface IWorkoutRepository {
    Workout getWorkout (int id);
    List<Workout> getAll ();
}
