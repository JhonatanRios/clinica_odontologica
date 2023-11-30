package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dto.entrada.odontologo.OdontologoEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.paciente.DomicilioEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.paciente.PacienteEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;
import com.backend.clinicaodontologica.exceptions.BadRequestException;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class TurnoServiceTest {
    @Autowired
    private TurnoService turnoService;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private OdontologoService odontologoService;

    @Test
    @Order(1)
    void deberiaRegistrarTurno_AlOdontologoConId1() throws BadRequestException {

        PacienteEntradaDto pacienteEntradaDto = new PacienteEntradaDto("John", "Doe", 123456789, LocalDate.of(2024, 05, 01), new DomicilioEntradaDto("Arenales", 2547, "Barrio Norte", "Buenos Aires"));
        OdontologoEntradaDto odontologoEntradaDto = new OdontologoEntradaDto("12000000", "b", "Doe");

        PacienteSalidaDto pacienteSalidaDto = pacienteService.registrarPaciente(pacienteEntradaDto);
        OdontologoSalidaDto odontologoSalidaDto = odontologoService.registrarOdontologo(odontologoEntradaDto);

        TurnoEntradaDto turnoEntradaDto = new TurnoEntradaDto(LocalDateTime.now(), 1L,1L);

        TurnoSalidaDto turnoSalidaDto = turnoService.registrarTurno(turnoEntradaDto);

        assertNotNull(turnoSalidaDto);
        assertEquals(1L, turnoSalidaDto.getOdontologo().getId());
    }
    @Test
    @Order(2)
    void deberiaDevolverUnaListaConUnTurno() {
        List<TurnoSalidaDto> turnoSalidaDtos = turnoService.listarTurnos();

        assertFalse(turnoSalidaDtos.isEmpty());
    }
    @Test
    @Order(3)
    void alIntentarEliminarUnTurnoId1YaEliminado_deberiaEliminarTurnoId1() {
        try {
            turnoService.eliminarTurno(1L);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(ResourceNotFoundException.class, ()-> turnoService.eliminarTurno(1L));
    }

}