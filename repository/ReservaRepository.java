package com.example.demohotelreservasapi.repository;

import com.example.demohotelreservasapi.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    List<Reserva> findByStatus(String status);
    //boolean findByCheckInLessThanEqualAndCheckOutGreaterThanEqualAndStatusNotAndHotelId(int hotelId, LocalDate checkIn);
}