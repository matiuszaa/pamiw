package com.example.mwo.app.factory;

import com.example.mwo.app.dto.ParkingDto;
import com.example.mwo.app.dto.ReservationDto;
import com.example.mwo.app.entity.Parking;
import com.example.mwo.app.entity.Reservation;

public class ReservationFactory {
    public ReservationDto mapToReservationDto(Reservation reservation) {
        return ReservationDto.builder()
                .endDate(reservation.getEndDate())
                .parkingAdress(reservation.getParking().getAdress())
                .spaceSignature(reservation.getParkingSpot().getSpaceSignature())
                .startDate(reservation.getStartDate()).build();
    }
}
