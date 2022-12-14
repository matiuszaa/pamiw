package com.example.mwo.app.service;

import java.util.List;

import com.example.mwo.app.dto.ParkingDto;
import com.example.mwo.app.dto.ReservationDto;
import com.example.mwo.app.entity.Parking;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

public interface ParkingService {

    public List<ParkingDto> getParkingList();
    public List<ParkingDto> showAvailableParkings();
    public ParkingDto reserveParkingSpot(ParkingDto theParking);
    public List<ReservationDto> getReservations();
    public void releaseSpace(String spaceSignature, String parking);

    Page<Parking> findParkingsWithOpenSpaces(int pageNum, String sortField, String sortDir);
}
