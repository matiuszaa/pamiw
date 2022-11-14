package com.example.mwo.app.dto;

import lombok.Builder;

@Builder
public class ReservationDto {
    private String parkingAdress;
    private String spaceSignature;
    private String startDate;
    private String endDate;
}
