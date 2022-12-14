package com.example.mwo.app.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    public Reservation(User user, Parking parkingId, ParkingSpot parkingSpot, String startDate, String endDate) {
        this.user = user;
        this.parkingId = parkingId;
        this.parkingSpot = parkingSpot;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="parking_id")
    private Parking parkingId;

    @ManyToOne
    @JoinColumn(name="parking_spot")
    private ParkingSpot parkingSpot;

    @Column(name="start_date")
    private String startDate;

    @Column(name="end_date")
    private String endDate;

}
