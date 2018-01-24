package ru.aintech.workoutmanager.persistence;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * @author Yaremchuk E.N. (aka Aintech)
 */

@Component
public class UserRepositoryImpl implements UserRepository {
    
//    private static final List<User> users = new ArrayList<>();
    
    private static final Map<String, User> users = new HashMap<>();
    
    static {
        if (users.isEmpty()) {
            users.put("manny", new User("manny", "manny@email", "pass"));
        }
    }
    
    @Override
    public User getUser(String username) {
        return users.get(username);
//        return users.stream().filter(user -> user.getUsername().toLowerCase().equals(username.toLowerCase())).collect(Collectors.toList()).get(0);
    }

    @Override
    public User save(User user) {
        users.put(user.getUsername(), user);
        return users.get(user.getUsername());
    }
}