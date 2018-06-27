package ru.aintech.workoutmanager.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Yaremchuk E.N. (aka Aintech)
 */

@Entity(name = "_workout")
public class Workout implements Serializable {
    
    @Id
    @Column(name = "_id")
    private int id;
    
    @Column(name = "_name")
    private String name;
    
    @OneToMany(mappedBy = "workout")
    private List<Exercise> exercises;
    
    private transient boolean nextToPerform;
    
    private transient String lastPerformTime;
    
    public Workout () {}
    
    public Workout(int id, String name, List<Exercise> exercises) {
        this.id = id;
        this.name = name;
        this.exercises = exercises;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }
    
    public String getColor () {
        return nextToPerform? "aqua": "azure;";
    }

    public boolean isNextToPerform() {
        return nextToPerform;
    }

    public void setNextToPerform(boolean nextToPerform) {
        this.nextToPerform = nextToPerform;
    }

    public String getLastPerformTime() {
        return lastPerformTime;
    }

    public void setLastPerformTime(String lastPerformTime) {
        this.lastPerformTime = lastPerformTime;
    }
    
    public Workout getCopy () {
        List<Exercise> exerCopy = new ArrayList<>();
        getExercises().forEach(exercise -> exerCopy.add(exercise.getCopy()));
        return new Workout(id, name, exerCopy);
    }
}