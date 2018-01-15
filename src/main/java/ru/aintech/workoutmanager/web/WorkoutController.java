package ru.aintech.workoutmanager.web;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.aintech.workoutmanager.persistence.Exercise;
import ru.aintech.workoutmanager.persistence.IWorkoutRepository;
import ru.aintech.workoutmanager.persistence.Repeat;
import ru.aintech.workoutmanager.persistence.Workout;
import ru.aintech.workoutmanager.persistence.WorkoutScoreManager;

/**
 *
 * @author Yaremchuk E.N. (aka Aintech)
 */

@Controller
@RequestMapping("/workout")
public class WorkoutController {
    
    private final IWorkoutRepository repo;
    
    private Workout workout;
    
    private Exercise exercise;
    
    private int nextExerciseIndex;
    
    private int nextSetIndex;
    
    @Autowired
    public WorkoutController(IWorkoutRepository repo) {
        this.repo = repo;
    }
    
    @RequestMapping(value = "/{workoutId}", method = RequestMethod.GET)
    public String workout (@PathVariable("workoutId") Integer workoutId, Model model) {
        nextExerciseIndex = nextSetIndex = -1;
        workout = repo.getWorkout(workoutId);
        exercise = null;
        model.addAttribute("workout", workout);
        model.addAttribute("nextBtnName", nextExerciseIndex == workout.getExercises().length - 1? "Finish Workout" : "Begin Workout");
        return "workout";
    }
    
    @RequestMapping(value = "/{workoutId}", method = RequestMethod.POST)
    public String exerciseSwitch (@PathVariable("workoutId") Integer workoutId, @RequestParam(value = "action") String action, Model model) {
        workout = repo.getWorkout(workoutId);
        if (!model.containsAttribute("workout")) {
            model.addAttribute("workout", workout);
        }
        if (action.equals("next")) {
            if (exercise == null) {
                nextExerciseIndex++;
                nextSetIndex++;
            } else {
                if (nextSetIndex < exercise.getRepeats().length-1) {
                    nextSetIndex++;
                } else {
                    nextSetIndex = 0;
                    nextExerciseIndex++;
                }
            }
        } else if (action.matches("\\d+")) {
            workout.getExercises()[nextExerciseIndex].getRepeats()[nextSetIndex].setDone(Integer.parseInt(action));
            if (nextSetIndex < exercise.getRepeats().length-1) {
                nextSetIndex++;
            } else {
                nextSetIndex = 0;
                nextExerciseIndex++;
            }
        }
        
        if (nextExerciseIndex == workout.getExercises().length) {
            WorkoutScoreManager.getInstance().persistWorkoutScore(workout);
            return "redirect:/";
        }
        
        exercise = workout.getExercises()[nextExerciseIndex];
        Repeat repeat = exercise.getRepeats()[nextSetIndex];
        model.addAttribute("exercise", exercise);
        model.addAttribute("repeats", Arrays.asList(exercise.getRepeats()));
        model.addAttribute("repeat", repeat);
        model.addAttribute("nextBtnName", nextExerciseIndex == workout.getExercises().length - 1? "Finish Workout": (nextSetIndex < exercise.getRepeats().length-1)? "Next Set": "Next Exercise");
        return "workout";
    }
}