package ru.aintech.workoutmanager.persistence;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Yaremchuk E.N. (aka Aintech)
 */
public class WorkoutScoreManager {
    
    private final File scoreFile;
    
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    
    private static final WorkoutScoreManager instance = new WorkoutScoreManager();
    
    public static WorkoutScoreManager getInstance () { return instance; }
    
    private WorkoutScoreManager () {
        scoreFile = new File (new File(Thread.currentThread().getContextClassLoader().getResource("").getFile()).getParentFile().getParentFile(), "src\\main\\resources\\data\\WorkoutScore.txt");
    }
    
    public void persistWorkoutScore (Workout workout) {
        StringBuilder workoutScore = new StringBuilder("\n");
        workoutScore.append(workout.getName()).append(" - ").append(DATE_FORMAT.format(new Date())).append("\n");
        for (Exercise exercise : workout.getExercises()) {
            String score = exercise.getName() + " (" + exercise.getWeight() + " кг)" + " :";
            for (Repeat rep : exercise.getRepeats()) {
                score += " " + rep;
            }
            workoutScore.append(score).append("\n");
        }
        try {
            FileWriter writer = new FileWriter(scoreFile, true);
            writer.write(new String(workoutScore.toString().getBytes(StandardCharsets.UTF_8)));
            writer.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}