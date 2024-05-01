package com.example.demohotelreservasapi.web.controller;

import com.example.demohotelreservasapi.web.dto.ReservaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demohotelreservasapi.service.ReservaService;
import com.example.demohotelreservasapi.entity.Reserva;


@RestController
@RequestMapping("/api/v1/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @PostMapping("/criar")
    public ResponseEntity<String> criarReserva(@RequestBody ReservaDto reservaDto) {
        Reserva reserva = reservaService.criarReserva(reservaDto);
        return new ResponseEntity<>("Reserva criada com sucesso! ID: " + reserva.
                getId(), HttpStatus.CREATED);
    }
}

