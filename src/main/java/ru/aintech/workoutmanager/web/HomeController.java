package ru.aintech.workoutmanager.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.aintech.workoutmanager.persistence.IWorkoutRepository;
import ru.aintech.workoutmanager.persistence.WorkoutRepository;

/**
 * @author Yaremchuk E.N. (aka Aintech)
 */

@Controller
@RequestMapping({ "/", "/homepage" })
public class HomeController {
    
    @Autowired
    private IWorkoutRepository repo;
    
    public HomeController (IWorkoutRepository repo) {
        this.repo = repo;
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String home (Model model) {
        model.addAttribute("workouts", repo.getAll());
        return "home";
    }
}