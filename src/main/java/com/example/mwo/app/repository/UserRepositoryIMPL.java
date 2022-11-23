package com.example.mwo.app.repository;

import com.example.mwo.app.config.HibernateUtil;
import com.example.mwo.app.entity.Parking;
import com.example.mwo.app.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class UserRepositoryIMPL implements UserRepository {

    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public void registerUser(User registeredUser) {
        Session session = sessionFactory.openSession();
        session.save(registeredUser);

    }

    @Override
    public User validateUser(User loggingUser) {
        Session session = sessionFactory.openSession();

        String hql = "FROM User u where u.email = :email and u.password = :password";

        Query query = session.createQuery(hql, User.class);
        query.setParameter("email", loggingUser.getEmail());
        query.setParameter("password", loggingUser.getPassword());
        if(query.getSingleResult() == null) {
            return null;
        }

        return loggingUser;
    }

    @Override
    public List<String> showAvailableCities() {
        Session session = sessionFactory.openSession();

        String hql = "SELECT DISTINCT Parking.cityName FROM Parking";

        Query query = session.createQuery(hql, Parking.class);

        return query.getResultList();
    }
}
