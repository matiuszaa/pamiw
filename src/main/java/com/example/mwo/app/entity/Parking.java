package com.example.mwo.app.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="parking")
public class Parking {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @OneToMany(mappedBy = "parking", cascade = CascadeType.ALL)
    List<ParkingSpot> spaces;

    @Column(name="city")
    private String cityName;

    @Column(name="adress")
    private String adress;

    @Column(name="spaces_in_total")
    private int spacesInTotal;

    @Column(name="free_spaces")
    private int freeSpaces;

    @Column(name="disabled_spaces")
    private int disabledSpaces;

    @Column(name="price")
    private int price;

}