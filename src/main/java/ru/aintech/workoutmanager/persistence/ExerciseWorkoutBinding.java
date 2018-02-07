package ru.aintech.workoutmanager.persistence;

/**
 * @author Yaremchuk E.N. (aka Aintech)
 */
public class ExerciseWorkoutBinding {
    
    private int id;
    
    private Exercise exercise;
    
    private Workout workout;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }
}