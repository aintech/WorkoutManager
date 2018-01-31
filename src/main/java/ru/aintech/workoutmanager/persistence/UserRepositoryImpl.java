package ru.aintech.workoutmanager.persistence;

import java.io.Serializable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Yaremchuk E.N. (aka Aintech)
 */

@Primary
@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {
    
    private final SessionFactory sessionFactory;
    
    @Autowired
    public UserRepositoryImpl (SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    private Session getCurrentSession () {
        return sessionFactory.getCurrentSession();
    }
    
    @Override
    public User getUser(String username) {
        return (User) getCurrentSession().createCriteria(User.class).add(Restrictions.eq("username", username)).list().get(0);
    }

    @Override
    public User save(User user) {
        Serializable id = getCurrentSession().save(user);
        return new User((Integer) id, user.getUsername(), user.getPassword());
    }
}