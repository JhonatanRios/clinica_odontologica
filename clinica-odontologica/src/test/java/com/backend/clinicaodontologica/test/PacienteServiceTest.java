package com.backend.clinicaodontologica.test;

import com.backend.clinicaodontologica.dto.entrada.paciente.DomicilioEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.paciente.PacienteEntradaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.backend.clinicaodontologica.service.impl.PacienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PacienteServiceTest {
    @Autowired
    private PacienteService pacienteService;
    @Test
    void deberiaRetornarElNombreDeUnPacienteCorrecto(){
        PacienteEntradaDto pacienteEntradaDto = new PacienteEntradaDto("Jhon", "Doe", 41195253, LocalDate.of(2023, 11, 29), new DomicilioEntradaDto("Cabildo", 123, "Núñez", "Buenos Aires"));
        PacienteSalidaDto pacienteSalidaDto = pacienteService.registrarPaciente(pacienteEntradaDto);
        assertEquals("Jhon", pacienteSalidaDto.getNombre());
    }
    @Test
    void deberiaRetornarElApellidoDeUnPacienteCorrecto(){
        PacienteEntradaDto pacienteEntradaDto = new PacienteEntradaDto("Jhon", "Doe", 41195253, LocalDate.of(2023, 11, 29), new DomicilioEntradaDto("Cabildo", 123, "Núñez", "Buenos Aires"));
        PacienteSalidaDto pacienteSalidaDto = pacienteService.registrarPaciente(pacienteEntradaDto);
        assertEquals("Doe", pacienteSalidaDto.getApellido());
    }
}
