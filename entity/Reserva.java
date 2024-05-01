package com.example.demohotelreservasapi.entity;

import jakarta.persistence.*;

import lombok.*;
import java.time.LocalDate;


@Table(name = "reserva")
@Entity(name = "Reserva")
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private int quartoId;
    private int hotelId;
    private String status;




}
