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
    
    private int nextRepeatIndex;
    
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
        nextExerciseIndex = nextRepeatIndex = -1;
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
        
        if (state == WorkoutState.FINISH && action.equals("empty")) {
            return finishWorkout();
        }
        
        workout = repo.getWorkout(workoutId);
        if (!model.containsAttribute("workout")) {
            model.addAttribute("workout", workout);
        }
        
        switch (action) {
            case "beginWorkout": beginWorkout(); break;
            case "empty": proceedTraining(); break;
            case "skip": case "next": proceedTraining(); break;
            case "finishWorkout": return finishWorkout();
        }
        
        if (action.matches("\\d+")) {
            proceedWithValue(Integer.parseInt(action));
        }
        
        boolean noRepeat = nextExerciseIndex < workout.getExercises().length && (workout.getExercises()[nextExerciseIndex].getRepeats() == null || workout.getExercises()[nextExerciseIndex].getRepeats().length == 0);
        
        if (noRepeat && nextExerciseIndex == workout.getExercises().length - 1) {
            state = WorkoutState.FINISH;
        } else {
            if (nextExerciseIndex == workout.getExercises().length) {
                state = WorkoutState.FINISH;
            } else {
                if (nextRepeatIndex < exercise.getRepeats().length) {
                    for (int i = 0; i < exercise.getRepeats().length; i++) {
                        exercise.getRepeats()[i].setStyleClass(i == nextRepeatIndex? "setInProgress": i < nextRepeatIndex? "setDone": "setNotStarted");
                    }
                    Repeat repeat = exercise.getRepeats()[nextRepeatIndex];
                    model.addAttribute("repeat", repeat);
                }
            }
        }
        
        model.addAttribute("state", state);
        
        if (state != WorkoutState.FINISH) {
            for (int i = 0; i < 5; i++) {
                exerciseParts[i] = exerciseFull[i + exercisePartIndex];
                if (exerciseParts[i] != null) {
                    exerciseParts[i].setCurrentPart(i == 2);
                }
            }
            exercisePartIndex++;
            model.addAttribute("exerciseParts", exerciseParts);
            model.addAttribute("exercise", exercise);
        }
        
        model.addAttribute("recoveryPeriod", state == WorkoutState.RECOVERY_SET? 30: state == WorkoutState.RECOVERY_EXERCISE? 240: 0);
        
        return "workout";
    }
    
    private void setupExercise () {
        exercise = workout.getExercises()[nextExerciseIndex];
        //2 null впереди, repeat, recovery, repeat, recovery, repeat... + в конце долгий recovery после упражнения, 2 null в конце
        exerciseFull = new ExercisePart[2 + exercise.getRepeats().length * 2 + 2];
        int repeatsCounter = 0;
        for (int i = 2; i < exerciseFull.length-2; i++) {
            if (i % 2 == 0) {
                if (exercise.getRepeats()[repeatsCounter].getNeed() == 0) {
                    exerciseFull[i] = new ExercisePart("External Link");
                } else {
                    exerciseFull[i] = new ExercisePart(getTitleForRepeat(repeatsCounter + 1), exercise.getRepeats()[repeatsCounter]);
                }
                repeatsCounter++;
            } else {
                boolean workoutFinish = nextExerciseIndex == (workout.getExercises().length - 1) && (i == exerciseFull.length - 3);
                if (workoutFinish) {
                    exerciseFull[i] = new ExercisePart("Finish");
                } else {
                    exerciseFull[i] = new ExercisePart("Recovery", i == exerciseFull.length-3? 240: 30);
                }
            }
        }
        for (Exercise exer : workout.getExercises()) {
            exer.setStyleClass(exer == exercise? "exerciseBlockChecked": "exerciseBlockNotChecked");
        }
    }
    
    private void beginWorkout () {
        state = WorkoutState.TRAINING;
        nextExerciseIndex = 0;
        exercisePartIndex = 0;
        nextRepeatIndex = 0;
        setupExercise();
    }
    
    private void proceedTraining () {
        switch (state) {
            case BEGIN:
                beginWorkout();
                break;
            case TRAINING:
                Repeat repeat = workout.getExercises()[nextExerciseIndex].getRepeats()[nextRepeatIndex];
                proceedWithValue(repeat.getNeed());
                break;
            case RECOVERY_SET:
                nextRepeatIndex++;
                state = WorkoutState.TRAINING;
                break;
            case RECOVERY_EXERCISE:
                nextRepeatIndex = 0;
                nextExerciseIndex++;
                exercisePartIndex = 0;
                if (nextExerciseIndex == workout.getExercises().length) {
                    state = WorkoutState.FINISH;
                } else {
                    state = WorkoutState.TRAINING;
                    setupExercise();
                }
                break;
        }
    }
    
    private void proceedWithValue (Integer value) {
        workout.getExercises()[nextExerciseIndex].getRepeats()[nextRepeatIndex].setDone(value);
        if (nextRepeatIndex < exercise.getRepeats().length-1) {
            state = WorkoutState.RECOVERY_SET;
        } else {
            if (nextExerciseIndex == (workout.getExercises().length - 1) && nextRepeatIndex == (workout.getExercises()[nextExerciseIndex].getRepeats().length - 1)) {
                state = WorkoutState.FINISH;
            } else {
                state = WorkoutState.RECOVERY_EXERCISE;
            }
        }
    }
    
    private String finishWorkout () {
        new WorkoutScoreManager().persistWorkoutScore(workout);
        return "redirect:/";
    }
    
    private String getTitleForRepeat (int index) {
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