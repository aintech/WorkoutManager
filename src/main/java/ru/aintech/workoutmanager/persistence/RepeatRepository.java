package ru.aintech.workoutmanager.persistence;

import java.util.List;

/**
 *
 * @author Yaremchuk E.N. (aka Aintech)
 */
public interface RepeatRepository {
    Repeat save (Repeat repeat);
    Repeat getRepeat (int id);
}