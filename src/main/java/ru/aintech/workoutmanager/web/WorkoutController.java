package ru.aintech.workoutmanager.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.aintech.workoutmanager.persistence.IWorkoutRepository;

/**
 *
 * @author Yaremchuk E.N. (aka Aintech)
 */

@Controller
@RequestMapping("/workout")
public class WorkoutController {
    
    private final IWorkoutRepository repo;

    @Autowired
    public WorkoutController(IWorkoutRepository repo) {
        this.repo = repo;
    }
    
    @RequestMapping(value = "/{workoutId}", method = RequestMethod.GET)
    public String workout (@PathVariable("workoutId") Integer workoutId, Model model) {
        model.addAttribute("workout", repo.getWorkout(workoutId));
        return "workout";
    }
}