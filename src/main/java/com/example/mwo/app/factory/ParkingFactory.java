package com.example.mwo.app.factory;

import com.example.mwo.app.dto.ParkingDto;
import com.example.mwo.app.entity.Parking;

public class ParkingFactory {

    public Parking mapToParking(ParkingDto parkingDto) {
        return Parking.builder()
                .adress(parkingDto.getAdress())
                .cityName(parkingDto.getCityName())
                .disabledSpaces(parkingDto.getDisabledSpaces())
                .freeSpaces(parkingDto.getFreeSpaces())
                .price(parkingDto.getPrice())
                .spaces(parkingDto.getSpaces())
                .spacesInTotal(parkingDto.getSpacesInTotal()).build();
    }

    public ParkingDto mapToParkingDto(Parking parkingDto) {
        return ParkingDto.builder()
                .adress(parkingDto.getAdress())
                .cityName(parkingDto.getCityName())
                .disabledSpaces(parkingDto.getDisabledSpaces())
                .freeSpaces(parkingDto.getFreeSpaces())
                .price(parkingDto.getPrice())
                .spaces(parkingDto.getSpaces())
                .spacesInTotal(parkingDto.getSpacesInTotal()).build();
    }
}
