package com.backend.clinicaodontologica.controller;

import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.TurnoModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;
import com.backend.clinicaodontologica.exceptions.BadRequestException;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaodontologica.service.ITurnoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@Tag(name = "Turno")
@RequestMapping("/turnos")
public class TurnoController {
    private final ITurnoService turnoService;

    public TurnoController(ITurnoService turnoService) {
        this.turnoService = turnoService;
    }

    //POST
    @Operation(summary = "Registro de un nuevo Turno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Turno guardado correctamente", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = TurnoSalidaDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping("/registrar")
    public ResponseEntity<TurnoSalidaDto> registrarTurno(@RequestBody @Valid TurnoEntradaDto turno) throws BadRequestException {
        return new ResponseEntity<>(turnoService.registrarTurno(turno), HttpStatus.CREATED);
    }



    //GET
    @Operation(summary = "Búsqueda de un Turno por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Turno obtenido correctamente", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = TurnoSalidaDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Id inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Turno no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<TurnoSalidaDto> obtenerTurnoPorId(@PathVariable Long id){
        return new ResponseEntity<>(turnoService.buscarTurnoPorId(id), HttpStatus.OK);
    }
    @Operation(summary = "Listado de todos los Turnos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de Turnos obtenido correctamente", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PacienteSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @GetMapping("/listar")
    public ResponseEntity<List<TurnoSalidaDto>> listarTurnos(){
        return new ResponseEntity<>(turnoService.listarTurnos(), HttpStatus.OK);
    }



    //PUT
    @Operation(summary = "Actualización de un Turno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Turno actualizado correctamente", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PacienteSalidaDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request",  content = @Content),
            @ApiResponse(responseCode = "404", description = "Turno no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "UServer error", content = @Content)
    })
    @PutMapping("/actualizar")
    public TurnoSalidaDto actualizarTurno(@RequestBody TurnoModificacionEntradaDto turno) throws ResourceNotFoundException, BadRequestException {
        return turnoService.actualizarTurno(turno);
    }



    //DELETE
    @Operation(summary = "Eliminación de un Turno por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Turno eliminado correctamente", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "Id inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Turno no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<?> eliminarTurno(@PathVariable Long id) throws ResourceNotFoundException {
        turnoService.eliminarTurno(id);
        return new ResponseEntity<>("Turno eliminado correctamente", HttpStatus.OK);
    }
}