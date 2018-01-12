package ru.aintech.workoutmanager.persistence;

/**
 *
 * @author Yaremchuk E.N. (aka Aintech)
 */
public interface IUserRepository {
    User getUser(String username);
    User save (User user);
}