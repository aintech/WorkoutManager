package ru.aintech.workoutmanager.web;

import javax.servlet.http.Part;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import ru.aintech.workoutmanager.persistence.IUserRepository;
import ru.aintech.workoutmanager.persistence.User;

/**
 * @author Yaremchuk E.N. (aka Aintech)
 */
@Controller
@RequestMapping("/user")
public class UserController {
    
    IUserRepository repo;
    
    @Autowired
    public UserController (IUserRepository repo) {
        this.repo = repo;
    }
    
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegistrationForm (Model model) {
        model.addAttribute(new User());
        return "registerform";
    }
    
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String processRegistration (@RequestPart("profilePicture") Part profilePicture, @Valid User user, Errors errors) {
        if (errors.hasErrors()) {
            return "registerform";
        }
        repo.save(user);
        return "redirect:/user/" + user.getUsername();
    }
    
    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public String showUserProfile (@PathVariable String username, Model model) {
        User user = repo.getUser(username);
        model.addAttribute(user);
        return "profile";
    }
}