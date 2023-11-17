package com.backend.clinicaodontologica.service;

import java.util.List;

public interface ITurnoService {
    TurnoSalidaDto registrarOdontologo(TurnoEntradaDto odontologo);
    List<TurnoSalidaDto> listarOdontologos();
    TurnoSalidaDto buscarPacientePorDni(int dni);
    void eliminarPaciente(Long id);
    TurnoSalidaDto actualizarPaciente(TurnoModificacionEntradaDto paciente);
}