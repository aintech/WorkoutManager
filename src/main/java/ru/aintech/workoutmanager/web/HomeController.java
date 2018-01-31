package ru.aintech.workoutmanager.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.aintech.workoutmanager.persistence.UserRepository;
import ru.aintech.workoutmanager.persistence.WorkoutScheduleRepository;

/**
 * @author Yaremchuk E.N. (aka Aintech)
 */

@Controller
@RequestMapping({ "/", "/homepage" })
public class HomeController {
    
    private final WorkoutScheduleRepository repo;
    
    private final UserRepository userRepo;
    
    @Autowired
    public HomeController (WorkoutScheduleRepository repo, UserRepository userRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String home (Model model) {
        model.addAttribute("schedule", repo.getSchedule());
        System.out.println(userRepo.getUser("admin").getId());
        return "home";
    }
}