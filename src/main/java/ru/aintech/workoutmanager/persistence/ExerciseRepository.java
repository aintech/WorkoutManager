package ru.aintech.workoutmanager.persistence;

import java.util.List;

/**
 * @author Yaremchuk E.N. (aka Aintech)
 */
public interface ExerciseRepository {
    Exercise save (Exercise exercise);
    Exercise getExercise (int id);
}
