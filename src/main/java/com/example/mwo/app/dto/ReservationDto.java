package com.example.mwo.app.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReservationDto {
    private String parkingAdress;
    private String spaceSignature;
    private String startDate;
    private String endDate;
}
