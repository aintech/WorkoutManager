package ru.aintech.workoutmanager.persistence;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 * @author Yaremchuk E.N. (aka Aintech)
 */

@Component
public class ExerciseRepository implements IExerciseRepository {
    
    @Override
    public List<Exercise> getExercises (MuscleGroup muscleGroup) {
        return PersistenceRetriever.getInstance().getExercises().get(muscleGroup);
    }

    @Override
    public Exercise getExercise(int id) {
        return PersistenceRetriever.getInstance().getAllExercises().stream().filter(exer -> exer.getId() == id).collect(Collectors.toList()).get(0);
    }
}