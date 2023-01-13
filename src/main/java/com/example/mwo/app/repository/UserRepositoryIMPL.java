package com.example.mwo.app.repository;

import com.example.mwo.app.config.HibernateUtil;
import com.example.mwo.app.entity.Parking;
import com.example.mwo.app.entity.Role;
import com.example.mwo.app.entity.User;
import com.example.mwo.app.entity.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Repository
@Slf4j
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

    @Override
    public User saveUserAndRole(User person, Role role) {
        Session session = sessionFactory.openSession();

        String hql = "FROM User u where u.email= :email and u.password = :pwd";

        Query query = session.createQuery(hql, User.class);
        query.setParameter("email", person.getEmail());
        query.setParameter("pwd", person.getPassword());
        UserRole userRole = new UserRole(role.toString(), person);
        try {
            query.getSingleResult();
            log.error("User exists in db try once again");
            return null;
        } catch (NoResultException e) {
            session.beginTransaction();
            session.save(person);
            session.save(userRole);
            session.getTransaction().commit();
            session.close();
            return person;
        }

    }

    @Override
    public User findByUsername(String username) {
        Session session = sessionFactory.openSession();

        String hql = "FROM User u where u.email = :email";
        Query query = session.createQuery(hql, User.class);
        query.setParameter("email", username);
        return (User) query.getSingleResult();
    }

    public User readByCredentials(String email, String password) {
        Session session = sessionFactory.openSession();

        String hql = "FROM User u where u.email = :email and u.password = :password";

        Query query = session.createQuery(hql, User.class);
        query.setParameter("email", email);
        query.setParameter("password", password);
        try {
            User found = (User) query.getSingleResult();
            return found;
        } catch (NoResultException e) {
            return null;

        }
    }
}
