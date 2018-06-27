package ru.aintech.workoutmanager.persistence;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author Yaremchuk E.N. (aka Aintech)
 */

@Entity(name = "_repeat")
public class Repeat implements Serializable {
    
    @Id
    @Column(name = "_id")
    private int id;
    
    @ManyToOne
    @JoinColumn(name = "_exercise_id")
    private Exercise exercise;
    
    @Column(name = "_need")
    private int need;
    
    private transient int done;
    
    private transient String styleClass;

    public Repeat () {}
    
    public Repeat (int id, int need) {
        this(need);
        this.id = id;
    }
    
    public Repeat(int need) {
        this.need = need;
    }

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