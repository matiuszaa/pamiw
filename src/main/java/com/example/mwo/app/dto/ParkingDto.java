package com.example.mwo.app.dto;

import com.example.mwo.app.entity.ParkingSpot;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import java.util.List;

@Builder
@Getter
public class ParkingDto {
    List<ParkingSpot> spaces;

    private String cityName;

    private String adress;

    private int spacesInTotal;

    private int freeSpaces;

    private int disabledSpaces;

    private int price;
}
