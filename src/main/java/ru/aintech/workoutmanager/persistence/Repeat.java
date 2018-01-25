package ru.aintech.workoutmanager.persistence;

/**
 * @author Yaremchuk E.N. (aka Aintech)
 */
public class Repeat {
    
    private int need;
    
    private int done;
    
    private String styleClass;

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

    @Override
    public String toString() {
        return "" + done + "|" + need;
    }
    
    public void setStyleClass (String styleClass) {
        this.styleClass = styleClass;
    }
    
    public String getStyleClass () {
        return styleClass;
    }
}