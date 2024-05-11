package com.example.demohotelreservasapi.service;

import com.example.demohotelreservasapi.entity.Reserva;
import com.example.demohotelreservasapi.repository.ReservaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import java.util.List;
import java.util.stream.Collectors;

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
    public Reserva buscarPorId(int id) {
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
        int quartoId = reserva.getQuartoId();
        int hotelId = reserva.getHotelId();

        if (reservaRepository.reservasDuplicadas(quartoId, hotelId, checkIn)) {
            throw new ReservaDuplicadaException("Já existe uma reserva para o mesmo quarto, hotel e data.");
        }

        if (checkOut.isBefore(checkIn)) {
            throw new IllegalArgumentException("A data de check-out não pode ser antes da data de check-in.");
        }

        reserva.setStatus("ativa");
        return reservaRepository.save(reserva);
    }
    public void marcarReservasAtivasComoConcluidasAPartirDeHoje() {
        LocalDate hoje = LocalDate.now();
        List<Reserva> reservasCheckOutAntesDeHoje = reservaRepository.findByStatusAndCheckOutBefore("ativa", hoje);

        List<Integer> reservaIds = reservasCheckOutAntesDeHoje.stream().map(Reserva::getId).collect(Collectors.toList());

        if (!reservaIds.isEmpty()) {
            reservaRepository.marcarReservasComoConcluidas(reservaIds);
        }
    }
}