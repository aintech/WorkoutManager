package ru.aintech.workoutmanager.persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        workoutScore.append(workout.getId()).append(" ").append(workout.getName()).append(" - ").append(DATE_FORMAT.format(new Date())).append("\n");
        for (Exercise exercise : workout.getExercises()) {
            StringBuilder score = new StringBuilder();
            score.append(exercise.getName());
            if (exercise.getWeight() > 0) {
                score.append(" (").append(exercise.getWeight()).append(" кг)");
            }
            if (exercise.getRepeats().length > 0) {
                score.append(" :");
                for (Repeat rep : exercise.getRepeats()) {
                    score.append(" ").append(rep);
                }
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
        PersistenceRetriever.getInstance().rewriteWorkoutId();
    }
    
    public int getWorkoutId () {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(scoreFile), "UTF-8"));
            List<String> lines = new ArrayList<>();
            String line = null;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            
            for (int i = lines.size()-1; i >= 0; i--) {
                if (lines.get(i) != null && !lines.get(i).isEmpty()) {
                    if (lines.get(i).matches("\\d+.*")) {
                        String value = lines.get(i).substring(0, lines.get(i).indexOf(" "));
                        return Integer.parseInt(value) + 1;
                    }
                }
            }
        } catch (IOException  ex) {
            ex.printStackTrace();
        }
        return 1;
    }
}