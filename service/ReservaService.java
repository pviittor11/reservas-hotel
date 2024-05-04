package com.example.demohotelreservasapi.service;

import com.example.demohotelreservasapi.entity.Reserva;
import com.example.demohotelreservasapi.repository.ReservaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;

    @Transactional(readOnly = true)
    public List<Reserva> buscarTodos() {
        List<Reserva> reservas = reservaRepository.findAll();
        if (reservas.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma reserva encontrada");
        }
        return reservas;
    }

    @Transactional(readOnly = true)
    public Reserva buscarPorId(int id){
        return reservaRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Reserva id=%s não encontrado", id))
        );
    }
    @Transactional
    public List<Reserva> buscarPorStatus(String status) {
        return reservaRepository.findByStatus(status);
    }

    @Transactional
    public Reserva criarReserva(Reserva reserva) {
        LocalDate checkIn = reserva.getCheckIn();
        LocalDate checkOut = reserva.getCheckOut();

        if (checkOut.isBefore(checkIn)) {
            throw new IllegalArgumentException("A data de check-out não pode ser antes da data de check-in.");
        }

        reserva.setStatus("ativa");
        return reservaRepository.save(reserva);
    }
}