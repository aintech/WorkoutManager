package ru.aintech.workoutmanager.web;

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
    
    private WorkoutState state;
    
    @Autowired
    public WorkoutController(IWorkoutRepository repo) {
        this.repo = repo;
    }
    
    @RequestMapping(value = "/{workoutId}", method = RequestMethod.GET)
    public String workout (@PathVariable("workoutId") Integer workoutId, Model model) {
        nextExerciseIndex = nextSetIndex = -1;
        workout = repo.getWorkout(workoutId);
        exercise = null;
        state = WorkoutState.BEGIN;
        model.addAttribute("workout", workout);
        model.addAttribute("state", state);
        model.addAttribute("buttonName", "Begin Workout");
        return "workout";
    }
    
    @RequestMapping(value = "/{workoutId}", method = RequestMethod.POST)
    public String exerciseSwitch (@PathVariable("workoutId") Integer workoutId, @RequestParam(value = "action", defaultValue = "empty") String action, Model model) {
        if (state == WorkoutState.FINISH) {
            WorkoutScoreManager.getInstance().persistWorkoutScore(workout);
            return "redirect:/";
        }
        
        workout = repo.getWorkout(workoutId);
        if (!model.containsAttribute("workout")) {
            model.addAttribute("workout", workout);
        }
        
        if (action.equals("proceedAction")) {
            switch (state) {
                case BEGIN:
                    nextExerciseIndex = 0;
                    nextSetIndex = 0;
                    state = WorkoutState.PROCESS;
                    break;
                case PROCESS:
                    nextExerciseIndex++;
                    nextSetIndex = 0;
                    break;
            }
        } else if (action.matches("\\d+")) {
            workout.getExercises()[nextExerciseIndex].getRepeats()[nextSetIndex].setDone(Integer.parseInt(action));
            if (nextSetIndex < exercise.getRepeats().length-1) {
                nextSetIndex++;
            } else {
                nextSetIndex = 0;
                nextExerciseIndex++;
            }
        } else if (action.equals("empty")) {//Подразумевается, что была нажата клавиша Enter
            switch (state) {
                case BEGIN:
                    nextExerciseIndex = 0;
                    nextSetIndex = 0;
                    state = WorkoutState.PROCESS;
                    break;
                case PROCESS:
                    Exercise exer = workout.getExercises()[nextExerciseIndex];
                    if (exer != null && exer.getRepeats() != null && exer.getRepeats().length > 0) {
                        workout.getExercises()[nextExerciseIndex].getRepeats()[nextSetIndex].setDone(workout.getExercises()[nextExerciseIndex].getRepeats()[nextSetIndex].getNeed());
                    }
                    if (nextSetIndex < exercise.getRepeats().length-1) {
                        nextSetIndex++;
                    } else {
                        nextSetIndex = 0;
                        nextExerciseIndex++;
                    }
                    break;
            }
        }
        
        boolean noRepeat = nextExerciseIndex < workout.getExercises().length && (workout.getExercises()[nextExerciseIndex].getRepeats() == null || workout.getExercises()[nextExerciseIndex].getRepeats().length == 0);
        
        if (noRepeat && nextExerciseIndex == workout.getExercises().length - 1) {
            exercise = workout.getExercises()[nextExerciseIndex];
            state = WorkoutState.FINISH;
        } else {
            if (nextExerciseIndex == workout.getExercises().length) {
                state = WorkoutState.FINISH;
            } else {
                exercise = workout.getExercises()[nextExerciseIndex];
                if (nextSetIndex < exercise.getRepeats().length) {
                    Repeat repeat = exercise.getRepeats()[nextSetIndex];
                    model.addAttribute("repeat", repeat);
                }
            }
        }
        
        model.addAttribute("exercise", exercise);
        model.addAttribute("state", state);
        model.addAttribute("buttonName", state == WorkoutState.FINISH? "Finish Workout": "Next Exercise");
        return "workout";
    }
}