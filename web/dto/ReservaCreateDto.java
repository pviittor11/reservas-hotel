package com.example.demohotelreservasapi.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReservaCreateDto {
    @NotBlank
    private LocalDate checkIn;
    @NotBlank
    private LocalDate checkOut;
    @NotBlank
    private int quartoId;
    @NotBlank
    private int hotelId;
}