package ru.aintech.workoutmanager.persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
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
    
    private static final SimpleDateFormat SHORT_DATE_FORMAT = new SimpleDateFormat("dd MMM [HH:mm]");
    
    public WorkoutScoreManager () {
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
        defineWorkoutStatuses();
    }
    
    public void defineWorkoutStatuses () {
        int nextId = -1;
        Workout[] workouts = PersistenceManager.getInstance().getSchedules().get(0).getWorkouts();
        try {
            List<String> lines = new ArrayList<>();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(scoreFile), "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            
            List<String> generalInfo = new ArrayList<>();
            
            for (int i = lines.size()-1; i >= 0; i--) {
                if (lines.get(i) != null && !lines.get(i).isEmpty()) {
                    //Line which starts with numbers - is workout info line (id, name, time)
                    if (lines.get(i).matches("\\d+.*")) {
                        generalInfo.add(lines.get(i));
                    }
//                    if (lines.get(i).matches("\\d+.*")) {
//                        int id = Integer.parseInt(lines.get(i).substring(0, lines.get(i).indexOf(" ")).trim());
//                        String date = lines.get(i).substring(lines.get(i).indexOf(" - "), lines.get(i).length()).replace(" - ", "");
//                        //If next workout id yet not found
//                        if (nextId == -1) {
//                            if (id == workouts[workouts.length - 1].getId()) {
//                                nextId = workouts[0].getId();
//                            } else {
//                                for (int w = 0; w < workouts.length; w++) {
//                                    if (workouts[w].getId() == id) {
//                                        nextId = workouts[w + 1].getId();
//                                    }
//                                }
//                            }
//                        }
//                        
//                        for (Workout workout : workouts) {
//                             if (workout.getId() == id) {
//                                 workout.setLastPerformTime(SHORT_DATE_FORMAT.format(DATE_FORMAT.parse(date)));
//                             }
//                        }
//                        
//                        break;
//                    }
                }
            }
            
            int lastPerformedId = Integer.parseInt(generalInfo.get(generalInfo.size()-1).substring(0, generalInfo.get(generalInfo.size()-1).indexOf(" ")).trim());
            
        } catch (Exception  ex) {
            ex.printStackTrace();
        }
        
        for (Workout workout : workouts) {
            workout.setNextToPerform(workout.getId() == nextId);
        }
    }
}