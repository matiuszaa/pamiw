package com.example.mwo.app.repository;

import com.example.mwo.app.config.HibernateUtil;
import com.example.mwo.app.entity.Parking;
import com.example.mwo.app.entity.ParkingSpot;
import com.example.mwo.app.entity.Reservation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.time.Instant;
import java.util.List;


@Repository
public class ParkingRepositoryIMPL implements ParkingRepository{

    private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    @Transactional
    public List<Parking> getParkingList() {
        Session session = sessionFactory.openSession();

        String hql = "FROM Parking";

        Query query = session.createQuery(hql, Parking.class);
        List<Parking> parkings = query.getResultList();
        return parkings;
    }

    @Override
    public List<Parking> showAvailableParkings() {
        Session session = sessionFactory.openSession();
        String hql = "FROM Parking P WHERE P.freeSpaces > 0 ORDER BY P.freeSpaces";
        Query query = session.createQuery(hql);

        return query.getResultList();
    }

    @Override
    public ParkingSpot reserveParkingSpot(Parking theParking) {
        Session session = sessionFactory.openSession();
        String hql = "FROM Parking P WHERE P.id = :id";
        Query query = session.createQuery(hql);
        Parking parkingOfReservation = (Parking) query.setParameter("id", theParking.getId()).getSingleResult();
        if (parkingOfReservation == null) {
            throw new IllegalArgumentException("No parking found!!!");
        }
        if (parkingOfReservation.getFreeSpaces() <= 0) {
            throw new IllegalArgumentException("Parking has no more spaces left!");
        }
        ParkingSpot parkingSpot = getFirstFreeSpace(parkingOfReservation);
        parkingSpot.setIsOccupied(1);
        session.update(parkingSpot);
        return parkingSpot;
    }

    @Override
    public List<Reservation> getReservations() {
        Session session = sessionFactory.openSession();
        String hql = "FROM Reservation R WHERE R.user.id = :id";
        Query query = session.createQuery(hql);
        query.setParameter("id", 1);
        return query.getResultList();
    }

    @Override
    public void releaseSpace(String spaceSignature, Parking parking) {
        Session session = sessionFactory.openSession();
        String hql = "FROM Reservation R WHERE R.parkingId.id = :parking " +
                "and R.parkingSpot.spaceSignature = :spaceSignature and R.endDate = null";
        Query query = session.createQuery(hql);
        query.setParameter("parking", parking);
        query.setParameter("spaceSignature", spaceSignature);
        Reservation foundReservation = (Reservation) query.getSingleResult();
        foundReservation.setEndDate(Instant.now().toString());
        foundReservation.getParkingSpot().setIsOccupied(0);
        session.update(foundReservation);
    }

    private ParkingSpot getFirstFreeSpace(Parking parking) {
        for (ParkingSpot parkingSpot : parking.getSpaces()) {
            if (parkingSpot.getIsOccupied() == 0) {
                return parkingSpot;
            }
        }
        return null;
    }
}
