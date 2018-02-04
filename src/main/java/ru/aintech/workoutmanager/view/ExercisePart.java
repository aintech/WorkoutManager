package ru.aintech.workoutmanager.view;

import ru.aintech.workoutmanager.persistence.Repeat;

/**
 *
 * @author Yaremchuk E.N. (aka Aintech)
 */
public class ExercisePart {
    
    private String title;
    
    private String message;
    
    private Repeat repeat;
    
    private boolean recovery;
    
    private static int nextId;
    
    private int id;
    
    private boolean currentTimer = false;
    
    public ExercisePart (String title, String message) {
        this.title = title;
        this.message = message;
        recovery = true;
        id = nextId++;
    }
    
    public ExercisePart (String title, Repeat repeat) {
        this.title = title;
        this.repeat = repeat;
        recovery = false;
        id = nextId++;
    }
    
    public int getId () {
        return id;
    }
    
    public String getTitle () {
        return title;
    }
    
    public void setMessage (String message) {
        this.message = message;
    }
    
    public String getMessage () {
        if (repeat != null) {
            return (repeat.getDone() > 0? (repeat.getDone() + "/"): "") + repeat.getNeed();
        }
        return message;
    }
    
    public boolean isRecovery () {
        return recovery;
    }
    
    public void setCurrentTimer (boolean currentTimer) {
        this.currentTimer = currentTimer;
    }
    
    public String getIdentification () {
        return currentTimer? "recoveryTimer": "notActiveTimer";
    }
}
