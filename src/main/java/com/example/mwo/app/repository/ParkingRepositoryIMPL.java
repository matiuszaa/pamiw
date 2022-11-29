package com.example.mwo.app.repository;

import com.example.mwo.app.config.HibernateUtil;
import com.example.mwo.app.entity.Parking;
import com.example.mwo.app.entity.ParkingSpot;
import com.example.mwo.app.entity.Reservation;
import com.example.mwo.app.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.time.Instant;
import java.util.List;

@Repository
@Slf4j
public class ParkingRepositoryIMPL implements ParkingRepository{

    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    @Transactional
    public List<Parking> getParkingList() {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Session session = sessionFactory.openSession();

        String hql = "FROM Parking p where p.cityName = :userCity";

        Query query = session.createQuery(hql, Parking.class);
        query.setParameter("userCity", user.getCity());
        List<Parking> parkings = query.getResultList();
        return parkings;
    }

    @Override
    public List<Parking> showAvailableParkings() {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Session session = sessionFactory.openSession();
        String hql = "FROM Parking P WHERE P.freeSpaces > 0 and  P.cityName =  :city ORDER BY P.freeSpaces";
        Query query = session.createQuery(hql);
        query.setParameter("city", user.getCity());
        return query.getResultList();
    }

    @Override
    public ParkingSpot reserveParkingSpot(Parking theParking) {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Session session = sessionFactory.openSession();
        String hql = "FROM Parking P WHERE P.adress = :id";
        Query query = session.createQuery(hql);
        Parking parkingOfReservation = (Parking) query.setParameter("id", theParking.getAdress()).getSingleResult();
        if (parkingOfReservation == null) {
            log.error("No parking found");
            throw new IllegalArgumentException("No parking found!!!");
        }
        if (parkingOfReservation.getFreeSpaces() <= 0) {
            log.error("Parking has no more spaces left!");
            throw new IllegalArgumentException("Parking has no more spaces left!");
        }
        ParkingSpot parkingSpot = getFirstFreeSpace(parkingOfReservation);
        Reservation reservation = new Reservation(user, parkingOfReservation, parkingSpot, Instant.now().toString().substring(0, 10), null);
        parkingSpot.setIsOccupied(1);
        parkingOfReservation.setFreeSpaces(parkingOfReservation.getFreeSpaces() - 1);
        session.close();
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(parkingSpot);
        session.update(parkingOfReservation);
        session.save(reservation);
        session.getTransaction().commit();
        session.close();
        return parkingSpot;
    }

    @Override
    public List<Reservation> getReservations() {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Session session = sessionFactory.openSession();
        String hql = "FROM Reservation R WHERE R.user.id = :id order by R.endDate";
        Query query = session.createQuery(hql);
        query.setParameter("id", user.getId());
        return query.getResultList();
    }

    @Override
    public void releaseSpace(String spaceSignature, String parkingAdress) {
        Session session = sessionFactory.openSession();
        String parkinghql = "FROM Parking P WHERE P.adress = :parkingAdress";
        Query queryParking = session.createQuery(parkinghql);
        queryParking.setParameter("parkingAdress", parkingAdress);
        Parking parking = (Parking) queryParking.getSingleResult();

        String hql = "FROM Reservation R WHERE R.parkingId.id = :parking " +
                "and R.parkingSpot.spaceSignature = :spaceSignature and R.endDate = null";
        Query query = session.createQuery(hql);
        query.setParameter("parking", parking.getId());
        query.setParameter("spaceSignature", spaceSignature);
        Reservation foundReservation = (Reservation) query.getSingleResult();
        foundReservation.setEndDate(Instant.now().toString().substring(0, 10));
        ParkingSpot parkingSpot = getParkingSpot(parking, spaceSignature);
        parkingSpot.setIsOccupied(0);
        parking.setFreeSpaces(parking.getFreeSpaces() + 1);
        session.close();
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(foundReservation);
        session.update(parking);
        session.update(parkingSpot);
        session.getTransaction().commit();
        session.close();
    }

    private ParkingSpot getFirstFreeSpace(Parking parking) {
        for (ParkingSpot parkingSpot : parking.getSpaces()) {
            if (parkingSpot.getIsOccupied() == 0) {
                return parkingSpot;
            }
        }
        return null;
    }

    private ParkingSpot getParkingSpot(Parking parking, String spaceSignature) {
        for (ParkingSpot parkingSpot : parking.getSpaces()) {
            if (parkingSpot.getSpaceSignature().equals(spaceSignature)) {
                return parkingSpot;
            }
        }
        log.error("No parking spot found");
        throw new IllegalArgumentException("wrong data input");
    }

    private User readByCredentials(String email, String password) {
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
