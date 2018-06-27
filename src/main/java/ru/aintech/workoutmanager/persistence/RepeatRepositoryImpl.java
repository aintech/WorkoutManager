package ru.aintech.workoutmanager.persistence;

import java.io.Serializable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Yaremchuk E.N. (aka Aintech)
 */

@Repository
@Transactional
public class RepeatRepositoryImpl implements RepeatRepository {

    private final SessionFactory sessionFactory;
    
    @Autowired
    public RepeatRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public Session getCurrentSession () {
        return sessionFactory.getCurrentSession();
    }
    
    @Override
    public Repeat save(Repeat repeat) {
        Serializable id = getCurrentSession().save(repeat);
        return new Repeat(((Repeat)id).getId(), repeat.getNeed());
    }

    @Override
    public Repeat getRepeat(int id) {
        return getCurrentSession().get(Repeat.class, id);
    }
}