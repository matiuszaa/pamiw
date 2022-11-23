package com.example.mwo.app.dto;

import com.example.mwo.app.entity.ParkingSpot;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Builder
@Getter
public class ParkingDto {
    List<ParkingSpot> spaces;

    private String cityName;

    private String adress;

    private int spacesInTotal;

    private int freeSpaces;

    @Override
    public String toString() {
        return "ParkingDto{" +
                "spaces=" + spaces +
                ", cityName='" + cityName + '\'' +
                ", adress='" + adress + '\'' +
                ", spacesInTotal=" + spacesInTotal +
                ", freeSpaces=" + freeSpaces +
                ", disabledSpaces=" + disabledSpaces +
                ", price=" + price +
                '}';
    }

    private int disabledSpaces;

    private int price;
}
