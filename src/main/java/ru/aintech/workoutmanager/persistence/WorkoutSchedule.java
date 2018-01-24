package ru.aintech.workoutmanager.persistence;

/**
 * @author Yaremchuk E.N. (aka Aintech)
 */
public class WorkoutSchedule {
    
    private int id;
    
    private String name;
    
    private Workout[] workouts;

    public WorkoutSchedule(int id, String name, Workout[] workouts) {
        this.id = id;
        this.name = name;
        this.workouts = workouts;
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

    public Workout[] getWorkouts() {
        return workouts;
    }

    public void setWorkouts(Workout[] workouts) {
        this.workouts = workouts;
    }
}