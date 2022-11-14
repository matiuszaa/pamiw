package com.example.mwo.app.service;

import java.util.List;

import com.example.mwo.app.dto.ParkingDto;
import com.example.mwo.app.dto.ReservationDto;
import com.example.mwo.app.entity.Parking;
import com.example.mwo.app.entity.ReservedParking;

public interface ParkingService {

    public List<ParkingDto> getParkingList();
    public List<String> showAvailableParkings();
    public ParkingDto reserveParkingSpot(ParkingDto theParking);
    public List<ReservationDto> getReservations();
    public void releaseSpace(String[] list);
}
