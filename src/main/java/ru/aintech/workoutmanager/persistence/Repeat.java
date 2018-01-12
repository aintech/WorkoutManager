package ru.aintech.workoutmanager.persistence;

/**
 * @author Yaremchuk E.N. (aka Aintech)
 */
public class Repeat {
    
    private int need;
    
    private int done;

    public Repeat(int need) {
        this.need = need;
    }

    public int getNeed() {
        return need;
    }

    public void setNeed(int need) {
        this.need = need;
    }

    public int getDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }
}