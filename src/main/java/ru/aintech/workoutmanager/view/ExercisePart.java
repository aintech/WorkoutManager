package ru.aintech.workoutmanager.view;

import ru.aintech.workoutmanager.persistence.Repeat;

/**
 *
 * @author Yaremchuk E.N. (aka Aintech)
 */
public class ExercisePart {
    
    private final String title;
    
    private Repeat repeat;
    
    private boolean currentPart = false;
    
    private int recoveryPeriod = 0;
    
    public ExercisePart (String title) {
        this.title = title;
    }
    
    public ExercisePart (String title, Repeat repeat) {
        this.title = title;
        this.repeat = repeat;
    }
    
    public ExercisePart (String title, int recoveryPeriod) {
        this.title = title;
        this.recoveryPeriod = recoveryPeriod;
    }
    
    public String getTitle () {
        return title;
    }
    
    public String getMessage () {
        if (repeat != null) {
            if (repeat.getNeed() == 0) {
                return " ";
            }
            return repeat.getDone() > 0? (repeat.getDone() + "/" + repeat.getNeed()): (repeat.getNeed() + " reps");
        }
        if (recoveryPeriod > 0) {
            return recoveryPeriod + " sec";
        }
        return " ";
    }
    
    public int getRecoveryPeriod () {
        return recoveryPeriod;
    }
    
    public void setCurrentPart (boolean currentPart) {
        this.currentPart = currentPart;
    }
    
    public String getIdentification () {
        return recoveryPeriod == 0? getTitle(): currentPart? "currentTimer": "otherTimer";
    }
    
    public String getStyleClass () {
        return currentPart? "currentPart": "otherPart";
    }
}