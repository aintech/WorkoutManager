package ru.aintech.workoutmanager.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.aintech.workoutmanager.persistence.Exercise;
import ru.aintech.workoutmanager.persistence.ExerciseRepository;
import ru.aintech.workoutmanager.persistence.MuscleGroup;

/**
 * @author Yaremchuk E.N. (aka Aintech)
 */

@Controller
@RequestMapping("/exercises")
public class ExerciseController {
    
    private final ExerciseRepository repo;
    
    @Autowired
    public ExerciseController (ExerciseRepository repo) {
        this.repo = repo;
    }
    
//    @RequestMapping(method = RequestMethod.GET)
//    public List<Exercise> exercises (@RequestParam(value = "group", defaultValue = "BACK") MuscleGroup muscleGroup) {
//        return repo.getExercises(muscleGroup);
//    }
    
    @RequestMapping(value = "/{exerciseId}", method = RequestMethod.GET)
    public String exercise (@PathVariable("exerciseId") Integer exerciseId, Model model) {
        model.addAttribute("exercise", repo.getExercise(exerciseId));
        return "exercises";
    }
}