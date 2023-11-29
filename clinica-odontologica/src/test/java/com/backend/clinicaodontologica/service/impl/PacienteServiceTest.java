package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dto.entrada.paciente.DomicilioEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.paciente.PacienteEntradaDto;
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
class PacienteServiceTest {
    @Autowired
    private  PacienteService pacienteService;

    @Test
    @Order(1)
    void deberiaRegistrarPacienteNombreSofi_retornarId() throws BadRequestException {
        PacienteEntradaDto pacienteEntradaDto = new PacienteEntradaDto("Sofi", "Perez", 123456, LocalDate.of(2023, 12, 24), new DomicilioEntradaDto("calle", 1234, "Localidad", "Provincia"));

        PacienteSalidaDto pacienteSalidaDto = pacienteService.registrarPaciente(pacienteEntradaDto);

        assertNotNull(pacienteSalidaDto);
        assertEquals("Sofi", pacienteSalidaDto.getNombre());
    }

    @Test
    @Order(2)
    void alIntentarEliminarUnPacienteId1YaEliminado_deberiaEliminarPacienteId1() {
        try {
            pacienteService.eliminarPaciente(1L);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(ResourceNotFoundException.class, ()-> pacienteService.eliminarPaciente(1L));
    }

    @Test
    @Order(3)
    void deberiaDevolverUnaListaVaciaDePacientes() {
        List<PacienteSalidaDto> pacienteSalidaDtos = pacienteService.listarPacientes();

        assertTrue(pacienteSalidaDtos.isEmpty());
    }
}