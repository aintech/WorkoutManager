package ru.aintech.workoutmanager.persistence;

import java.util.Objects;

/**
 * @author Yaremchuk E.N. (aka Aintech)
 */
public class Exercise {
    
    private final int id;
    
    private final MuscleGroup muscleGroup;
    
    private final String name;
    
    private final int weight;
    
    private final int[] repeats;
    
    private final String external;
    
    public Exercise (int id, MuscleGroup muscleGroup, String name, int weight, int[] repeats, String external) {
        this.id = id;
        this.muscleGroup = muscleGroup;
        this.name = name;
        this.weight = weight;
        this.repeats = repeats;
        this.external = external;
    }

    public int getId() {
        return id;
    }

    public MuscleGroup getMuscleGroup() {
        return muscleGroup;
    }

    public int getWeight() {
        return weight;
    }
    
    public String getName() {
        return name;
    }
    
    public int[] getRepeats() {
        return repeats;
    }

    public String getExternal() {
        return external;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Exercise other = (Exercise) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
}