package com.example.mwo.app.entity;

import lombok.*;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="parking_spot")
public class ParkingSpot {

    @OneToMany(mappedBy="parkingSpot")
    private List<Reservation> spotsList;

    @Id
    @Column(name="id")
    private int id;

    @Column(name="space_name")
    private String spaceSignature;

    @ManyToOne
    @JoinColumn(name = "parking_id")
    private Parking parking;

    @Column(name="is_occupied")
    private int isOccupied;

    @Column(name="is_disabled_space")
    private int isDisabledSpace;

}
