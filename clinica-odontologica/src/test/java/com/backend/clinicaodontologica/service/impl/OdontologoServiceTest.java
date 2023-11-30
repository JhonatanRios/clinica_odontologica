package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dto.entrada.odontologo.OdontologoEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.paciente.DomicilioEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.paciente.PacienteEntradaDto;
import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.backend.clinicaodontologica.exceptions.BadRequestException;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class OdontologoServiceTest {
    @Autowired
    private  OdontologoService odontologoService;

    @Test
    @Order(1)
    void deberiaRegistrarOdontologoMatricula1A_retornarId() throws BadRequestException {
        OdontologoEntradaDto odontologoEntradaDto = new OdontologoEntradaDto("1A", "Sofia", "Apellido");

        OdontologoSalidaDto odontologoSalidaDto = odontologoService.registrarOdontologo(odontologoEntradaDto);

        assertNotNull(odontologoSalidaDto);
        assertEquals("1A", odontologoSalidaDto.getMatricula());
    }

    @Test
    @Order(2)
    void alIntentarEliminarUnOdontologoId1YaEliminado_deberiaEliminarodontologoId1() {
        try {
            odontologoService.eliminarOdontologo(1L);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(ResourceNotFoundException.class, ()-> odontologoService.eliminarOdontologo(1L));
    }

    @Test
    @Order(3)
    void deberiaDevolverUnaListaVaciaDeOdontologos() {
        List<OdontologoSalidaDto> odontologoSalidaDtos = odontologoService.listarOdontologos();

        assertTrue(odontologoService.listarOdontologos().isEmpty());
    }

}