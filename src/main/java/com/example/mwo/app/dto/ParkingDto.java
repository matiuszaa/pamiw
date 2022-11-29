package com.example.mwo.app.dto;

import com.example.mwo.app.entity.ParkingSpot;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParkingDto {
    List<ParkingSpot> spaces;

    private String cityName;

    private String adress;

    private Integer spacesInTotal;

    private Integer freeSpaces;

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

    private Integer disabledSpaces;

    private Integer price;
}
