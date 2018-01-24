package ru.aintech.workoutmanager.persistence;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 * @author Yaremchuk E.N. (aka Aintech)
 */

@Component
public class ExerciseRepositoryImpl implements ExerciseRepository {
    
    @Override
    public List<Exercise> getExercises (MuscleGroup muscleGroup) {
        return PersistenceManager.getInstance().getExercises().get(muscleGroup);
    }

    @Override
    public Exercise getExercise(int id) {
        return PersistenceManager.getInstance().getAllExercises().stream().filter(exer -> exer.getId() == id).collect(Collectors.toList()).get(0);
    }
}