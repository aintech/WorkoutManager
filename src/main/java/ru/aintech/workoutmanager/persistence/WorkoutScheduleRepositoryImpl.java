package ru.aintech.workoutmanager.persistence;

import org.springframework.stereotype.Component;

/**
 * @author Yaremchuk E.N. (aka Aintech)
 */
@Component
public class WorkoutScheduleRepositoryImpl implements WorkoutScheduleRepository {

    @Override
    public WorkoutSchedule getSchedule() {
        return PersistenceManager.getInstance().getSchedules().get(0);
    }
}