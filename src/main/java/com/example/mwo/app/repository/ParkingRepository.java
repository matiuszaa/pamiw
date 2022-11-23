package com.example.mwo.app.repository;

import com.example.mwo.app.entity.Parking;
import com.example.mwo.app.entity.ParkingSpot;
import com.example.mwo.app.entity.Reservation;
import com.example.mwo.app.entity.ReservedParking;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingRepository {

    public List<Parking> getParkingList();

    public List<Parking> showAvailableParkings();

    public ParkingSpot reserveParkingSpot(Parking theParking);

    public List<Reservation> getReservations();

    public void releaseSpace(String spaceSignature, Parking parking);
}
