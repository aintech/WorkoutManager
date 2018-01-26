package ru.aintech.workoutmanager.web;

/**
 * @author Yaremchuk E.N. (aka Aintech)
 */
public enum WorkoutState {
    BEGIN, TRAINING, RECOVERY_SET, RECOVERY_EXERCISE, FINISH;
    
    public String getName() {
        return name();
    }
}