package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dto.entrada.odontologo.OdontologoEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.OdontologoModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;
import com.backend.clinicaodontologica.entity.Odontologo;
import com.backend.clinicaodontologica.repository.OdontologoRepository;
import com.backend.clinicaodontologica.service.IOdontologoService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class OdontologoService implements IOdontologoService {
    private final Logger LOGGER = LoggerFactory.getLogger(OdontologoService.class);
    private final OdontologoRepository odontologoRepository;
    private final ModelMapper modelMapper;

    public OdontologoService(OdontologoRepository odontologoRepository, ModelMapper modelMapper) {
        this.odontologoRepository = odontologoRepository;
        this.modelMapper = modelMapper;
        configureMapping();
    }

    @Override
    public OdontologoSalidaDto registrarOdontologo(OdontologoEntradaDto odontologo) {
        return null;
    }

    @Override
    public List<OdontologoSalidaDto> listarOdontologos() {
        return null;
    }

    @Override
    public OdontologoSalidaDto buscarOdontologoPorId(Long id) {
        return null;
    }

    @Override
    public void eliminarOdontologo(Long id) {

    }

    @Override
    public OdontologoSalidaDto actualizarOdontologo(OdontologoModificacionEntradaDto odontologo) {
        return null;
    }

    private void configureMapping() {
        modelMapper.typeMap(OdontologoEntradaDto.class, Odontologo.class);
        modelMapper.typeMap(Odontologo.class, OdontologoSalidaDto.class);
        modelMapper.typeMap(OdontologoModificacionEntradaDto.class, Odontologo.class);
    }
}