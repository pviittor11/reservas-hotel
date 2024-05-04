package com.example.demohotelreservasapi.web.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReservaResponseDto {
    private int id;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private int quartoId;
    private int hotelId;
    private String status;
}