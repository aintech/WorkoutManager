package ru.aintech.workoutmanager.persistence;

/**
 * @author Yaremchuk E.N. (aka Aintech)
 */
public enum MuscleGroup {
    ARMS("Руки"),
    SHOULDERS("Плечи"),
    BACK("Спина"),
    CHEST("Грудь"),
    ABS("Пресс"),
    LEGS("Ноги");
    
    public final String description;
    
    MuscleGroup (String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}