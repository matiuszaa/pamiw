package com.example.mwo.app.service;

import java.util.ArrayList;
import java.util.List;

import com.example.mwo.app.dto.ParkingDto;
import com.example.mwo.app.dto.ReservationDto;
import com.example.mwo.app.entity.Parking;
import com.example.mwo.app.entity.Reservation;
import com.example.mwo.app.factory.ParkingFactory;
import com.example.mwo.app.factory.ReservationFactory;
import com.example.mwo.app.repository.ParkingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ParkingServiceImpl implements ParkingService {

    @Autowired
    private ParkingRepository parkingRepository;

    private ParkingFactory parkingFactory = new ParkingFactory();
    private ReservationFactory reservationFactory = new ReservationFactory();

    @Override
    @Transactional
    public List<ParkingDto> getParkingList() {
        List<Parking> parkings = parkingRepository.getParkingList();
        List<ParkingDto> parkingDtos = new ArrayList<>();
        for (Parking parking : parkings) {
            ParkingDto actualDto = parkingFactory.mapToParkingDto(parking);
            if (actualDto.getCityName() == null || actualDto.getAdress() == null) {
                log.error("Cannot retrieve parking lists because some fields were invalid");
                throw new IllegalArgumentException("Invalid fields");
            }
            parkingDtos.add(actualDto);
        }
        return parkingDtos;
    }

    @Override
    @Transactional
    public List<String> showAvailableParkings() {
        List<Parking> parkings = parkingRepository.showAvailableParkings();
        List<String> parkingAdresses = new ArrayList<>();
        for (Parking parking : parkings) {
            parkingAdresses.add(parking.getAdress());
        }

        return parkingAdresses;
    }

    @Override
    @Transactional
    public ParkingDto reserveParkingSpot(ParkingDto theParking) {
        if (theParking.getAdress() == null || theParking.getCityName() == null || theParking.getCityName() == null){
            log.error("Cannot reserve parking spot because of wrong fields i ParkingDTO");
            throw new IllegalArgumentException("Invalid fields");

        }

        Parking parking = parkingFactory.mapToParking(theParking);
        parkingRepository.reserveParkingSpot(parking);

        return theParking;
    }

    @Override
    @Transactional
    public List<ReservationDto> getReservations() {
        List<Reservation> reservations = parkingRepository.getReservations();
        List<ReservationDto> reservationDtos = new ArrayList<>();
        for (Reservation reservation : reservations) {
            reservationDtos.add(reservationFactory.mapToReservationDto(reservation));
        }

        return reservationDtos;
    }

    @Override
    @Transactional
    public void releaseSpace(String[] list) {
        parkingRepository.releaseSpace(list);

    }

}
