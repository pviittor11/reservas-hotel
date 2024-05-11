package com.example.demohotelreservasapi.web.controller;

import com.example.demohotelreservasapi.entity.Reserva;
import com.example.demohotelreservasapi.repository.ReservaRepository;
import com.example.demohotelreservasapi.service.ReservaDuplicadaException;
import com.example.demohotelreservasapi.service.ReservaService;
import com.example.demohotelreservasapi.web.dto.ReservaCreateDto;
import com.example.demohotelreservasapi.web.dto.ReservaResponseDto;
import com.example.demohotelreservasapi.web.dto.mapper.ReservaMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    @PostMapping("/atualizar-reservas")
    @Operation(summary = "Atualizar reservas ativas para concluídas", description = "Atualiza o status das reservas ativas cujo checkout ocorre antes da data atual para 'concluída'")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservas atualizadas com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro ao atualizar reservas")
    })
    public ResponseEntity<String> atualizarReservas() {
        try {
            reservaService.marcarReservasAtivasComoConcluidasAPartirDeHoje();
            return ResponseEntity.ok("Reservas atualizadas com sucesso para 'concluída'");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar reservas: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<ReservaResponseDto>> getAll() {
        List<Reserva> reservas = reservaService.buscarTodos();
        return ResponseEntity.ok(ReservaMapper.toListDto(reservas));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDto> getById(@PathVariable int id){
        Reserva reserva = reservaService.buscarPorId(id);
        return ResponseEntity.ok(ReservaMapper.toDto(reserva));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Listar hotéis por status de reserva ativa", description = "Listar hotéis por status de reserva ativa",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista os hotéis com base em um status de reserva ativa",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ReservaResponseDto.class))))
            }
    )
    public ResponseEntity<List<ReservaResponseDto>> getByStatus(@PathVariable String status) {
        List<Reserva> reservas = reservaService.buscarPorStatus(status);
        return ResponseEntity.ok(ReservaMapper.toListDto(reservas));
    }

    @PostMapping
    @Operation(summary = "Criar uma nova reserva", description = "Cria uma nova reserva se não houver duplicatas",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Reserva criada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Já existe uma reserva para o mesmo hotel e período")
            }
    )
    public ResponseEntity<String> criarReserva(@Valid @RequestBody ReservaCreateDto reservaCreateDto) {
        Reserva novaReserva = reservaService.criarReserva(ReservaMapper.toReserva(reservaCreateDto));
        return ResponseEntity.status(HttpStatus.CREATED).body("Reserva criada com sucesso! ID: " + novaReserva.getId());
    }
}