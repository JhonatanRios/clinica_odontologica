package com.backend.clinicaodontologica.service;

import com.backend.clinicaodontologica.dto.entrada.odontologo.OdontologoEntradaDto;

import java.util.List;

public interface IOdontologoService {
    OdontologoSalidaDto registrarOdontologo(OdontologoEntradaDto odontologo);
    List<OdontologoSalidaDto> listarOdontologos();
    OdontologoSalidaDto buscarPacientePorDni(int dni);
    void eliminarPaciente(Long id);
    OdontologoSalidaDto actualizarPaciente(OdontologoModificacionEntradaDto paciente);
}