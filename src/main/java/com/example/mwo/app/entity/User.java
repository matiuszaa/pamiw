package com.example.mwo.app.entity;

import lombok.*;

import java.util.List;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="user")
public class User {

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="lastName")
    private String lastName;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="city")
    private String city = "Bialystok";

    @Column(name="is_disabled")
    private String isDisability;

    @Column(name="is_logged")
    private int isLogged;

    @Override
    public String toString() {
        return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + "]";
    }

}
