package com.backend.clinicaodontologica.controller;

import com.backend.clinicaodontologica.dto.entrada.paciente.PacienteEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.PacienteModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.backend.clinicaodontologica.exceptions.BadRequestException;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaodontologica.service.IPacienteService;
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
@Tag(name = "Paciente")
@RequestMapping("/pacientes")
public class PacienteController {
    private final IPacienteService pacienteService;

    public PacienteController(IPacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    //POST
    @Operation(summary = "Registro de un nuevo Paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Paciente guardado correctamente", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PacienteSalidaDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping("/registrar")
    public ResponseEntity<PacienteSalidaDto> registrarPaciente(@RequestBody @Valid PacienteEntradaDto paciente) throws BadRequestException {
        return new ResponseEntity<>(pacienteService.registrarPaciente(paciente), HttpStatus.CREATED);
    }



    //GET
    @Operation(summary = "Búsqueda de un Paciente por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente obtenido correctamente", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PacienteSalidaDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Id inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<PacienteSalidaDto> obtenerPacientePorId(@PathVariable Long id){
        return new ResponseEntity<>(pacienteService.buscarPacientePorId(id), HttpStatus.OK);
    }
    @Operation(summary = "Listado de todos los Pacientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de Pacientes obtenido correctamente", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PacienteSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @GetMapping("/listar")
    public ResponseEntity<List<PacienteSalidaDto>> listarPacientes(){
        return new ResponseEntity<>(pacienteService.listarPacientes(), HttpStatus.OK);
    }



    //PUT
    @Operation(summary = "Actualización de un Paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente actualizado correctamente", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = PacienteSalidaDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request",  content = @Content),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "UServer error", content = @Content)
    })
    @PutMapping("/actualizar")
    public PacienteSalidaDto actualizarPaciente(@RequestBody PacienteModificacionEntradaDto paciente) throws ResourceNotFoundException {
        return pacienteService.actualizarPaciente(paciente);
    }



    //DELETE
    @Operation(summary = "Eliminación de un Paciente por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Paciente eliminado correctamente", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "Id inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<?> eliminarPaciente(@PathVariable Long id) throws ResourceNotFoundException {
        pacienteService.eliminarPaciente(id);
        return new ResponseEntity<>("Paciente eliminado correctamente", HttpStatus.OK);
    }
}