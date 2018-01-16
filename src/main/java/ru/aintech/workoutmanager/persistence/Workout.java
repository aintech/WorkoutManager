package ru.aintech.workoutmanager.persistence;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Yaremchuk E.N. (aka Aintech)
 */
public class Workout {
    
    private final int id;
    
    private final String name;
    
    private final Exercise[] exercises;

    public Workout(int id, String name, Exercise[] exercises) {
        this.id = id;
        this.name = name;
        this.exercises = exercises;
    }

    public int getId() {
        return id;
    }

    public String getIdStr () {
        return String.valueOf(id);
    }
    
    public String getName() {
        return name;
    }

    public Exercise[] getExercises() {
        return exercises;
    }
    
    public String getColor () {
        return PersistenceRetriever.getInstance().getWorkoutColor(id);
    }
}