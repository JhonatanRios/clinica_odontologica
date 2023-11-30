package com.backend.clinicaodontologica.controller;

import com.backend.clinicaodontologica.dto.entrada.odontologo.OdontologoEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.OdontologoModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;
import com.backend.clinicaodontologica.exceptions.BadRequestException;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaodontologica.service.IOdontologoService;
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
@Tag(name = "Odontólogo")
@RequestMapping("/odontologos")
public class OdontologoController {
    private final IOdontologoService odontologoService;

    public OdontologoController(IOdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    //POST
    @Operation(summary = "Registro de un nuevo Odontólogo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Odontólogo guardado correctamente", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = OdontologoSalidaDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @PostMapping("/registrar")
    public ResponseEntity<OdontologoSalidaDto> registrarOdontologo(@RequestBody @Valid OdontologoEntradaDto odontologo) throws BadRequestException {
        return new ResponseEntity<>(odontologoService.registrarOdontologo(odontologo), HttpStatus.CREATED);
    }



    //GET
    @Operation(summary = "Búsqueda de un Odontólogo por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Odontólogo obtenido correctamente", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = OdontologoSalidaDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Id inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Odontólogo no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<OdontologoSalidaDto> obtenerOdontologoPorId(@PathVariable Long id){
        return new ResponseEntity<>(odontologoService.buscarOdontologoPorId(id), HttpStatus.OK);
    }
    @Operation(summary = "Listado de todos los Odontólogos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de odontólogos obtenido correctamente", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = OdontologoSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @GetMapping("/listar")
    public ResponseEntity<List<OdontologoSalidaDto>> listarOdontologos(){
        return new ResponseEntity<>(odontologoService.listarOdontologos(), HttpStatus.OK);
    }



    //PUT
    @Operation(summary = "Actualización de un Odontólogo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Odontólogo actualizado correctamente", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = OdontologoSalidaDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad Request",  content = @Content),
            @ApiResponse(responseCode = "404", description = "Odontólogo no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "UServer error", content = @Content)
    })
    @PutMapping("/actualizar")
    public OdontologoSalidaDto actualizarOdontologo(@RequestBody OdontologoModificacionEntradaDto odontologo) throws ResourceNotFoundException {
        return odontologoService.actualizarOdontologo(odontologo);
    }



    //DELETE
    @Operation(summary = "Eliminación de un odontólogo por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Odontólogo eliminado correctamente", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "Id inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Odontólogo no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<?> eliminarOdontologo(@PathVariable Long id) throws ResourceNotFoundException {
        odontologoService.eliminarOdontologo(id);
        return new ResponseEntity<>("Odontólogo eliminado correctamente", HttpStatus.OK);
    }
}