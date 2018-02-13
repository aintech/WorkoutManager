package ru.aintech.workoutmanager.api;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.aintech.workoutmanager.persistence.Workout;
import ru.aintech.workoutmanager.persistence.WorkoutRepository;

/**
 * @author Yaremchuk E.N. (aka Aintech)
 */
@Controller
@RequestMapping("/workoutapi")
public class WorkoutApiController {
    
    private WorkoutRepository workoutRepository;
    
    @Autowired
    public WorkoutApiController (WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public Workout workout (@RequestParam(value = "id", defaultValue = "0") int id) {
        return workoutRepository.getWorkout(id);
    }
}