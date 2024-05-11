package com.example.demohotelreservasapi.repository;

import com.example.demohotelreservasapi.entity.Reserva;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    List<Reserva> findByStatus(String status);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Reserva r WHERE r.quartoId = :quartoId AND r.hotelId = :hotelId AND r.checkIn = :checkIn")
    boolean reservasDuplicadas(@Param("quartoId") int quartoId, @Param("hotelId") int hotelId, @Param("checkIn") LocalDate checkIn);


    List<Reserva> findByStatusAndCheckOutBefore(String status, LocalDate checkOut);

    @Transactional
    @Modifying
    @Query("UPDATE Reserva r SET r.status = 'concluída' WHERE r.id IN :reservaIds")
    void marcarReservasComoConcluidas(@Param("reservaIds") List<Integer> reservaIds);
}

