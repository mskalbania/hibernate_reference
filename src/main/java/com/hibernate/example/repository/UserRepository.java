package com.hibernate.example.repository;

import com.hibernate.example.model.User;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UserRepository {

    private final SessionFactory sessionFactory;

    public UserRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public String save(User user) {
        return (String) sessionFactory.getCurrentSession().save(user);
    }

    public User getByUserNameLike(String userNamePattern) {
        return sessionFactory.getCurrentSession()
                .createQuery("select u from User u where u.userName like '%'||:pattern||'%' ", User.class)
                .setParameter("pattern", userNamePattern)
                .getSingleResult();
    }

    public void deleteAll() {
        sessionFactory.getCurrentSession()
                .createQuery("delete from User")
                .executeUpdate();
    }
}
