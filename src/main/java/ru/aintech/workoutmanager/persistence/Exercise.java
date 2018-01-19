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
    
    private final Repeat[] repeats;
    
    private final String external;
    
    private transient String styleClass = "exerciseBlockNotChecked";
    
    public Exercise (int id, MuscleGroup muscleGroup, String name, int weight, int[] repeats, String external) {
        this.id = id;
        this.muscleGroup = muscleGroup;
        this.name = name;
        this.weight = weight;
        this.repeats = new Repeat[repeats.length];
        for (int i = 0; i < repeats.length; i++) {
            this.repeats[i] = new Repeat(repeats[i]);
        }
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
    
    public Repeat[] getRepeats() {
        return repeats;
    }
    
    public String getExternal() {
        return external;
    }

    public Exercise getCopy () {
        int[] reps = new int[repeats.length];
        for (int i = 0; i < reps.length; i++) { reps[i] = repeats[i].getNeed(); }
        return new Exercise(id, muscleGroup, name, weight, reps, external);
    }
    
    public void setStyleClass (String styleClass) {
        this.styleClass = styleClass;
    }
    
    public String getStyleClass () {
        return styleClass;
    }
    
//    @Override
//    public int hashCode() {
//        int hash = 7;
//        hash = 79 * hash + this.id;
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) {
//            return true;
//        }
//        if (obj == null) {
//            return false;
//        }
//        if (getClass() != obj.getClass()) {
//            return false;
//        }
//        final Exercise other = (Exercise) obj;
//        if (this.id != other.id) {
//            return false;
//        }
//        return true;
//    }
}