package com.backend.clinicaodontologica.service;

import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.TurnoModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;

import java.util.List;

public interface ITurnoService {
    TurnoSalidaDto registrarTurno(TurnoEntradaDto odontologo);
    List<TurnoSalidaDto> listarTurnos();
    TurnoSalidaDto buscarTurnoPorId(Long id);
    void eliminarTurno(Long id);
    TurnoSalidaDto actualizarTurno(TurnoModificacionEntradaDto paciente);
}