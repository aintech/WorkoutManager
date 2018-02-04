package ru.aintech.workoutmanager.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.aintech.workoutmanager.persistence.Exercise;
import ru.aintech.workoutmanager.persistence.Repeat;
import ru.aintech.workoutmanager.persistence.Workout;
import ru.aintech.workoutmanager.persistence.WorkoutRepository;
import ru.aintech.workoutmanager.persistence.WorkoutScoreManager;
import ru.aintech.workoutmanager.view.ExercisePart;

/**
 *
 * @author Yaremchuk E.N. (aka Aintech)
 */

@Controller
@RequestMapping("/workout")
public class WorkoutController {
    
    private final WorkoutRepository repo;
    
    private Workout workout;
    
    private Exercise exercise;
    
    private int nextExerciseIndex;
    
    private int nextSetIndex;
    
    private WorkoutState state;
    
    private ExercisePart[] exerciseParts = new ExercisePart[5];
    
    private int exercisePartIndex = 0;
    
    private ExercisePart[] exerciseFull;
    
    @Autowired
    public WorkoutController(WorkoutRepository repo) {
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
        model.addAttribute("exerciseParts", exerciseParts);
        return "workout";
    }
    
    @RequestMapping(value = "/{workoutId}", method = RequestMethod.POST)
    public String exerciseSwitch (@PathVariable("workoutId") Integer workoutId, @RequestParam(value = "action", defaultValue = "empty") String action, Model model) {
        if (state == WorkoutState.FINISH) {
            new WorkoutScoreManager().persistWorkoutScore(workout);
            return "redirect:/";
        }
        
        workout = repo.getWorkout(workoutId);
        if (!model.containsAttribute("workout")) {
            model.addAttribute("workout", workout);
        }
        
        if (action.equals("proceedAction")) {
            switch (state) {
                case BEGIN:
                    state = WorkoutState.TRAINING;
                    nextExerciseIndex = 0;
                    exercisePartIndex = 0;
                    nextSetIndex = 0;
                    break;
                case TRAINING:
                    state = WorkoutState.RECOVERY_EXERCISE;
                    break;
                case RECOVERY_EXERCISE:
                    state = WorkoutState.TRAINING;
                    nextExerciseIndex++;
                    exercisePartIndex = 0;
                    nextSetIndex = 0;
                    break;
            }
        } else if (action.matches("\\d+")) {
            workout.getExercises()[nextExerciseIndex].getRepeats()[nextSetIndex].setDone(Integer.parseInt(action));
            if (nextSetIndex < exercise.getRepeats().length-1) {
                state = WorkoutState.RECOVERY_SET;
                nextSetIndex++;
            } else {
                state = WorkoutState.RECOVERY_EXERCISE;
                nextSetIndex = 0;
                nextExerciseIndex++;
                exercisePartIndex = 0;
            }
        } else if (action.equals("empty")) {//Подразумевается, что была нажата клавиша Enter
            switch (state) {
                case BEGIN:
                    state = WorkoutState.TRAINING;
                    nextExerciseIndex = 0;
                    exercisePartIndex = 0;
                    nextSetIndex = 0;
                    break;
                case TRAINING:
                    Exercise exer = workout.getExercises()[nextExerciseIndex];
                    if (exer != null && exer.getRepeats() != null && exer.getRepeats().length > 0) {
                        workout.getExercises()[nextExerciseIndex].getRepeats()[nextSetIndex].setDone(workout.getExercises()[nextExerciseIndex].getRepeats()[nextSetIndex].getNeed());
                    }
                    if (nextSetIndex < exercise.getRepeats().length-1) {
                        state = WorkoutState.RECOVERY_SET;
                        nextSetIndex++;
                    } else {
                        state = WorkoutState.RECOVERY_EXERCISE;
                        nextSetIndex = 0;
                        nextExerciseIndex++;
                        exercisePartIndex = 0;
                    }
                    break;
                case RECOVERY_SET:
                case RECOVERY_EXERCISE:
                    state = WorkoutState.TRAINING;
                    break;
            }
        }
        
        boolean noRepeat = nextExerciseIndex < workout.getExercises().length && (workout.getExercises()[nextExerciseIndex].getRepeats() == null || workout.getExercises()[nextExerciseIndex].getRepeats().length == 0);
        
        if (noRepeat && nextExerciseIndex == workout.getExercises().length - 1) {
            setupExercise();
            state = WorkoutState.FINISH;
        } else {
            if (nextExerciseIndex == workout.getExercises().length) {
                state = WorkoutState.FINISH;
            } else {
                setupExercise();
                if (nextSetIndex < exercise.getRepeats().length) {
                    for (int i = 0; i < exercise.getRepeats().length; i++) {
                        exercise.getRepeats()[i].setStyleClass(i == nextSetIndex? "setInProgress": i < nextSetIndex? "setDone": "setNotStarted");
                    }
                    Repeat repeat = exercise.getRepeats()[nextSetIndex];
                    model.addAttribute("repeat", repeat);
                }
            }
        }
        
        if (exercisePartIndex >= exerciseFull.length) {
            exercisePartIndex = 0;
        }
        for (int i = 0; i < 5; i++) {
            exerciseParts[i] = exerciseFull[i + exercisePartIndex];
            if (exerciseParts[i] != null && exerciseParts[i].isRecovery()) {
                exerciseParts[i].setCurrentTimer(i == 2);
            }
        }
        exercisePartIndex++;
        model.addAttribute("exerciseParts", exerciseParts);
        
        model.addAttribute("exercise", exercise);
        model.addAttribute("state", state);
        model.addAttribute("buttonName", state == WorkoutState.FINISH? "Finish Workout": "Next Exercise");
        return "workout";
    }
    
    private void setupExercise () {
        exercise = workout.getExercises()[nextExerciseIndex];
        exerciseFull = new ExercisePart[exercise.getRepeats().length + (exercise.getRepeats().length - 1) + 4];
        int repeatsCounter = 0;
        for (int i = 2; i < exerciseFull.length-2; i++) {
            if (i % 2 == 0) {
//                exerciseFull[i] = new ExercisePart(getTitleForSet(repeatsCounter+1), String.valueOf(exercise.getRepeats()[repeatsCounter].getNeed()));
                exerciseFull[i] = new ExercisePart(getTitleForSet(repeatsCounter+1), exercise.getRepeats()[repeatsCounter]);
                repeatsCounter++;
            } else {
                exerciseFull[i] = new ExercisePart("Recovery", "");
            }
        }
        for (Exercise exer : workout.getExercises()) {
            exer.setStyleClass(exer == exercise? "exerciseBlockChecked": "exerciseBlockNotChecked");
        }
    }
    
    private String getTitleForSet (int index) {
        return "Set " +
                (index == 1? "I":
                index == 2? "II":
                index == 3? "III":
                index == 4? "IV":
                index == 5? "V":
                index == 6? "VI":
                index == 7? "VII":
                index == 8? "VIII":
                index == 9? "XI":
                index == 10? "X":
                "unknown");
    }
}