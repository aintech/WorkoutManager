package ru.aintech.workoutmanager.persistence;

import java.util.List;

/**
 * @author Yaremchuk E.N. (aka Aintech)
 */
public interface ExerciseRepository {
    List<Exercise> getExercises (MuscleGroup muscleGroup);
    Exercise getExercise (int id);
}
