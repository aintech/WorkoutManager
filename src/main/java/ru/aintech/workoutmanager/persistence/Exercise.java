package ru.aintech.workoutmanager.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * @author Yaremchuk E.N. (aka Aintech)
 */

@Entity(name = "_exercise")
public class Exercise implements Serializable{
    
    @Id
    @Column(name = "_id")
    private int id;
    
    @ManyToOne()
    @JoinColumn(name = "_workout_id")
    private Workout workout;
    
    @Column(name = "_muscle_group")
    @Enumerated(EnumType.STRING)
    private MuscleGroup muscleGroup;
    
    @Column(name = "_name")
    private String name;
    
    @Column(name = "_weight")
    private int weight;
    
    @OneToMany(mappedBy = "exercise")
    private List<Repeat> repeats = new ArrayList<>();
    
    @Column(name = "_external")
    private String external;
    
    private transient String styleClass = "exerciseBlockNotChecked";
    
    public Exercise () {}
    
    public Exercise (int id, String name, int weight, List<Repeat> repeats, String external) {
        this(name, weight, repeats, external);
        this.id = id;
    }
    
    public Exercise (String name, int weight, List<Repeat> repeats, String external) {
        this.name = name;
        this.weight = weight;
        this.repeats = repeats;
        this.external = external;
    }
    
    public Exercise (int id, MuscleGroup muscleGroup, String name, int weight, int[] repeats, String external) {
        this.id = id;
        this.muscleGroup = muscleGroup;
        this.name = name;
        this.weight = weight;
        this.repeats = new ArrayList<>();// new Repeat[repeats.length];
        for (int i = 0; i < repeats.length; i++) {
            this.repeats.add(new Repeat(repeats[i]));
        }
        this.external = external;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public MuscleGroup getMuscleGroup() {
        return muscleGroup;
    }

    public void setMuscleGroup(MuscleGroup muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public List<Repeat> getRepeats() {
        return repeats;
    }

    public void setRepeats(List<Repeat> repeats) {
        this.repeats = repeats;
    }
    
    public String getExternal() {
        return external;
    }

    public void setExternal(String external) {
        this.external = external;
    }
    
    public Exercise getCopy () {
        int[] reps = new int[repeats.size()];
        for (int i = 0; i < reps.length; i++) { reps[i] = repeats.get(i).getNeed(); }
        return new Exercise(id, muscleGroup, name, weight, reps, external);
    }
    
    public void setStyleClass (String styleClass) {
        this.styleClass = styleClass;
    }
    
    public String getStyleClass () {
        System.out.println(getName() + " - " + getRepeats().size());
        return styleClass;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        return getId() == ((Exercise)obj).getId();
    }
}