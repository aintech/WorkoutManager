package ru.aintech.workoutmanager.persistence;

/**
 *
 * @author Yaremchuk E.N. (aka Aintech)
 */
public class Workout {
    
    private final int id;
    
    private final String name;
    
    private final Exercise[] exercises;
    
    private transient boolean nextToPerform;
    
    private transient String lastPerformTime;
    
    public Workout(int id, String name, Exercise[] exercises) {
        this.id = id;
        this.name = name;
        this.exercises = exercises;
    }

    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }

    public Exercise[] getExercises() {
        return exercises;
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
        Exercise[] exerCopy = new Exercise[exercises.length];
        for (int i = 0; i < exerCopy.length; i++) {
            exerCopy[i] = exercises[i].getCopy();
        }
        return new Workout(id, name, exerCopy);
    }
}