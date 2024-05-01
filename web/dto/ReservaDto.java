package com.example.demohotelreservasapi.web.dto;


import java.time.LocalDate;

public class ReservaDto {
    private LocalDate check_in;

    private LocalDate check_out;
    private int hotel_id;
    private int quarto_id;
    private String status;

    public int getQuartoId() {
        return quarto_id;
    }

    public int getHotelId() {
        return hotel_id;
    }

    public LocalDate getCheck_out() {
        return check_out;
    }

    public void setCheck_out(LocalDate checkOut) {
        this.check_out = checkOut;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getCheck_in() {
        return check_in;
    }

    public void setCheck_in(LocalDate checkIn) {
        this.check_in = checkIn;
    }
}

   
