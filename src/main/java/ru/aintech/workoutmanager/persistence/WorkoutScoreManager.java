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
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                }
            }
            
            int lastPerformedId = Integer.parseInt(generalInfo.get(0).substring(0, generalInfo.get(0).indexOf(" ")).trim());
            int nextId = -1;
            if (lastPerformedId == workouts[workouts.length - 1].getId()) {
                nextId = workouts[0].getId();
            } else {
                for (int w = 0; w < workouts.length; w++) {
                    if (workouts[w].getId() == lastPerformedId) {
                        nextId = workouts[w + 1].getId();
                    }
                }
            }
            
            for (Workout workout : workouts) { workout.setNextToPerform(workout.getId() == nextId); }
            
            Map<Integer, Workout> workoutsById = new HashMap<>();
            Arrays.asList(workouts).forEach(workout -> workoutsById.put(workout.getId(), workout));
            Integer id;
            Date date;
            for (String info : generalInfo) {
                id = Integer.parseInt(info.substring(0, info.indexOf(" ")).trim());
                date = DATE_FORMAT.parse(info.substring(info.indexOf(" - "), info.length()).replace(" - ", ""));
                workoutsById.get(id).setLastPerformTime(SHORT_DATE_FORMAT.format(date));
            }
        } catch (Exception  ex) {
            ex.printStackTrace();
        }
    }
}