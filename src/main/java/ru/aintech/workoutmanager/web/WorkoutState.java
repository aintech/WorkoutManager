package ru.aintech.workoutmanager.web;

/**
 * @author Yaremchuk E.N. (aka Aintech)
 */
public enum WorkoutState {
    BEGIN, TRAINING, RECOVERY, FINISH;
    
    public String getName() {
        return name();
    }
}