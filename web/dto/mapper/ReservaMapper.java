package com.example.demohotelreservasapi.web.dto.mapper;

import com.example.demohotelreservasapi.entity.Reserva;
import com.example.demohotelreservasapi.web.dto.ReservaCreateDto;
import com.example.demohotelreservasapi.web.dto.ReservaResponseDto;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;
public class ReservaMapper {
    public static ReservaResponseDto toDto(Reserva reserva) {
        ReservaResponseDto dto = new ReservaResponseDto();
        dto.setCheckIn(reserva.getCheckIn());
        dto.setCheckOut(reserva.getCheckOut());
        dto.setQuartoId(reserva.getQuartoId());
        dto.setHotelId(reserva.getHotelId());
        dto.setStatus(reserva.getStatus());
        return dto;
    }

    public static Reserva toReserva(ReservaCreateDto reservaCreateDto) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper.map(reservaCreateDto, Reserva.class);
    }

    public static List<ReservaResponseDto> toListDto(List<Reserva> reservas) {
        return reservas.stream().map(ReservaMapper::toDto).collect(Collectors.toList());
    }
    public static Reserva toEntity(ReservaResponseDto dto) {
        Reserva reserva = new Reserva();
        reserva.setCheckIn(dto.getCheckIn());
        reserva.setCheckOut(dto.getCheckOut());
        reserva.setQuartoId(dto.getQuartoId());
        reserva.setHotelId(dto.getHotelId());
        reserva.setStatus(dto.getStatus());
        return reserva;
    }
}
