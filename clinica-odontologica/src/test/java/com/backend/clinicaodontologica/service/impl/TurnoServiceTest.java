package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dto.entrada.paciente.DomicilioEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.paciente.PacienteEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;
import com.backend.clinicaodontologica.exceptions.BadRequestException;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class TurnoServiceTest {
    @Autowired
    private TurnoService turnoService;

    @Test
    @Order(1)
    void deberiaRegistrarTurnoAlOdontologoConId1() throws BadRequestException {
        PacienteEntradaDto pacienteEntradaDto = new PacienteEntradaDto("John", "Doe", 123456789, LocalDate.of(2024, 05, 01), new DomicilioEntradaDto("Arenales", 2547, "Barrio Norte", "Buenos Aires"));

    }

}