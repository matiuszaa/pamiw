package com.example.mwo.app.service;

import java.util.ArrayList;
import java.util.List;

import com.example.mwo.app.dto.ParkingDto;
import com.example.mwo.app.dto.ReservationDto;
import com.example.mwo.app.entity.Parking;
import com.example.mwo.app.entity.Reservation;
import com.example.mwo.app.entity.Role;
import com.example.mwo.app.entity.User;
import com.example.mwo.app.factory.ParkingFactory;
import com.example.mwo.app.factory.ReservationFactory;
import com.example.mwo.app.repository.ParkingRepository;
import com.example.mwo.app.repository.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ParkingServiceImpl implements ParkingService {

    @Autowired
    private ParkingRepository parkingRepository;

    @Autowired
    private ReservationRepository reservationRepository;

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
    public List<ParkingDto> showAvailableParkings() {
        List<Parking> parkings = parkingRepository.showAvailableParkings();
        List<ParkingDto> parkingDtos = new ArrayList<>();
        for (Parking parking : parkings) {
            parkingDtos.add(parkingFactory.mapToParkingDto(parking));
        }

        return parkingDtos;
    }

    @Override
    @Transactional
    public ParkingDto reserveParkingSpot(ParkingDto theParking) {
        if (theParking.getAdress() == null) {
            log.error("Cannot reserve parking spot because of wrong fields i ParkingDTO");
            throw new IllegalArgumentException("Invalid fields");
        }

        Parking parking = new Parking();
        parking.setAdress(theParking.getAdress());
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
    public void releaseSpace(String spaceSignature, String parking) {
        parkingRepository.releaseSpace(spaceSignature, parking);

    }

    @Override
    public Page<Parking> findParkingsWithOpenSpaces(int pageNum, String sortField, String sortDir) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                sortDir.equals("asc") ? Sort.by(sortField).ascending()
                        : Sort.by(sortField).descending());
        Page<Parking> parkingPage = reservationRepository.findByCityName(user.getCity()
                ,pageable);
        return parkingPage;
    }

}
