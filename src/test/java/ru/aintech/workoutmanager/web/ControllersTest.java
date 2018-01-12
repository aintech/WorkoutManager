package ru.aintech.workoutmanager.web;

import java.util.List;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceView;
import ru.aintech.workoutmanager.persistence.Exercise;
import ru.aintech.workoutmanager.persistence.ExerciseRepository;
import ru.aintech.workoutmanager.persistence.IExerciseRepository;
import ru.aintech.workoutmanager.persistence.IUserRepository;
import ru.aintech.workoutmanager.persistence.IWorkoutRepository;
import ru.aintech.workoutmanager.persistence.MuscleGroup;
import ru.aintech.workoutmanager.persistence.User;
import ru.aintech.workoutmanager.persistence.UserRepository;

/**
 * @author Yaremchuk E.N. (aka Aintech)
 */
public class ControllersTest {

    @Test
    public void homeController () throws Exception {
        IWorkoutRepository repo = Mockito.mock(IWorkoutRepository.class);
        HomeController controller = new HomeController(repo);
        MockMvc mock = MockMvcBuilders.standaloneSetup(controller).build();
        mock.perform(MockMvcRequestBuilders.get("/")).andExpect(MockMvcResultMatchers.view().name("home"));
    }
    
//    @Test
//    public void exerciseController () throws Exception {
//        List<Exercise> exercises = new ExerciseRepository().getExercises(MuscleGroup.ARMS);
//        IExerciseRepository mockRepos = Mockito.mock(IExerciseRepository.class);
//        Mockito.when(mockRepos.getExercises(MuscleGroup.ARMS)).thenReturn(exercises);
//        
//        ExerciseController controller = new ExerciseController(mockRepos);
//        MockMvc mock = MockMvcBuilders.standaloneSetup(controller).setSingleView(new InternalResourceView("/WEB-INF/pages/exercises.jsp")).build();
//        
//        mock.perform(MockMvcRequestBuilders.get("/exercises"))
//                .andExpect(MockMvcResultMatchers.view().name("exercises"))
//                .andExpect(MockMvcResultMatchers.model().attributeExists("exerciseList"))
//                .andExpect(MockMvcResultMatchers.model().attribute("exerciseList", Matchers.hasItems(exercises.toArray())));
//    }
    
    @Test
    public void exerciseControllerByBodyPart () throws Exception {
        List<Exercise> backExercises = new ExerciseRepository().getExercises(MuscleGroup.BACK);
        IExerciseRepository repo  = Mockito.mock(IExerciseRepository.class);
        Mockito.when(repo.getExercises(MuscleGroup.BACK)).thenReturn(backExercises);
        
        ExerciseController controller = new ExerciseController(repo);
        MockMvc mock = MockMvcBuilders.standaloneSetup(controller).setSingleView(new InternalResourceView("/WEB-INF/pages/exercises.jsp")).build();
        mock.perform(MockMvcRequestBuilders.get("/exercises?group=BACK"))
                .andExpect(MockMvcResultMatchers.view().name("exercises"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("exerciseList"))
                .andExpect(MockMvcResultMatchers.model().attribute("exerciseList", Matchers.hasItems(backExercises.toArray())));
    }
    
//    @Test
//    public void exerciseControllerById () throws Exception {
//        Exercise exercise = new ExerciseRepository().getExercise(1);
//        IExerciseRepository repo = Mockito.mock(IExerciseRepository.class);
//        Mockito.when(repo.getExercise(1)).thenReturn(exercise);
//        
//        ExerciseController controller = new ExerciseController(repo);
//        MockMvc mock = MockMvcBuilders.standaloneSetup(controller).build();
//        
//        mock.perform(MockMvcRequestBuilders.get("/exercises/1"))
//                .andExpect(MockMvcResultMatchers.view().name("exercises"))
//                .andExpect(MockMvcResultMatchers.model().attributeExists("exercise"))
//                .andExpect(MockMvcResultMatchers.model().attribute("exercise", exercise));
//    }
    
    @Test
    public void userController () throws Exception {
        IUserRepository repo = Mockito.mock(IUserRepository.class);
        UserController controller = new UserController(repo);
        MockMvc mock = MockMvcBuilders.standaloneSetup(controller).build();
        
        mock.perform(MockMvcRequestBuilders.get("/user/register"))
                .andExpect(MockMvcResultMatchers.view().name("registerform"));
    }
    
    public void userControllerRegistration () throws Exception {
        IUserRepository repo = Mockito.mock(IUserRepository.class);
        User unsaved = new User("man", "man@mail", "pass");
        User saved = new User("man", "man@mail", "pass");
        Mockito.when(repo.save(unsaved)).thenReturn(saved);
        
        UserController controller = new UserController(repo);
        MockMvc mock = MockMvcBuilders.standaloneSetup(controller).build();
        mock.perform(MockMvcRequestBuilders.post("/user/register")
                .param("username", "man")
                .param("email", "man@mail")
                .param("password", "pass"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/user/man"));
        Mockito.verify(repo, Mockito.atLeastOnce()).save(unsaved);
    }
}