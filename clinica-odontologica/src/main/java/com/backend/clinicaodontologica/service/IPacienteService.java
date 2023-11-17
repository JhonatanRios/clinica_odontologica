package com.backend.clinicaodontologica.service;

import com.backend.clinicaodontologica.dto.entrada.paciente.PacienteEntradaDto;

public interface IPacienteService {
    PacienteSalidaDto registarPaciente(PacienteEntradaDto paciente);
    List<PacienteSalidaDto> listarPacientes();
    PacienteSalidaDto buscarPacientePorDni(int dni);
    void eliminarPaciente(Long id);
    PacienteSalidaDto actualizarPaciente(PacienteModificacionEntradaDto paciente);
}