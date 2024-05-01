package com.example.demohotelreservasapi.service;

import com.example.demohotelreservasapi.entity.Reserva;
import com.example.demohotelreservasapi.repository.ReservaRepository;
import com.example.demohotelreservasapi.web.dto.ReservaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;

    @Autowired
    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    @Transactional
    public Reserva criarReserva(ReservaDto reservaDto){
        Reserva reserva = new Reserva();
        reserva.setCheckIn(reservaDto.getCheck_in());
        reserva.setCheckOut(reservaDto.getCheck_out());
        reserva.setQuartoId(reservaDto.getQuartoId());
        reserva.setHotelId(reservaDto.getHotelId());// Assuming hotelId is present in the DTO
        reserva.setStatus(reservaDto.getStatus());

        return reservaRepository.save(reserva);
    }

    @Transactional
    public Reserva save(Reserva reserva) {
        return reservaRepository.save(reserva);
    }
}
