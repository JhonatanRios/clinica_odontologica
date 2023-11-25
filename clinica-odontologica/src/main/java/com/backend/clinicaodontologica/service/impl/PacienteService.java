package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dto.entrada.paciente.PacienteEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.PacienteModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.backend.clinicaodontologica.entity.Paciente;
import com.backend.clinicaodontologica.exceptions.BadRequestException;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaodontologica.repository.PacienteRepository;
import com.backend.clinicaodontologica.service.IPacienteService;
import com.backend.clinicaodontologica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService implements IPacienteService {
    private final Logger LOGGER = LoggerFactory.getLogger(PacienteService.class);
    private final PacienteRepository pacienteRepository;
    private final ModelMapper modelMapper;

    public PacienteService(PacienteRepository pacienteRepository, ModelMapper modelMapper) {
        this.pacienteRepository = pacienteRepository;
        this.modelMapper = modelMapper;
        configureMapping();
    }

    @Override
    public PacienteSalidaDto registrarPaciente(PacienteEntradaDto paciente) throws BadRequestException {
        LOGGER.info("PacienteEntradaDto: " + JsonPrinter.toString(paciente));

        // Convertir de PacienteEntradaDto a Entidad por medio del mapper
        Paciente pacienteEntidad = modelMapper.map(paciente, Paciente.class);
        // Persistir a la capa de DAO y obtener entidad
        Paciente pacienteAPersistir = pacienteRepository.save(pacienteEntidad);
        // Convertir la entidad a PacienteSalidoDto
        PacienteSalidaDto pacienteSalidaDto = modelMapper.map(pacienteAPersistir, PacienteSalidaDto.class);

        LOGGER.info("PacienteSalidaDto: " + JsonPrinter.toString(pacienteSalidaDto));

        if (pacienteSalidaDto != null) {
            return pacienteSalidaDto;
        } else throw new BadRequestException("No se pudo registrar al paciente");
    }

    @Override
    public List<PacienteSalidaDto> listarPacientes() {
        List<PacienteSalidaDto> pacienteSalidaDto = pacienteRepository.findAll().stream().map(paciente -> modelMapper.map(paciente, PacienteSalidaDto.class)).toList();

        if (LOGGER.isInfoEnabled()) LOGGER.info("Listado de todos los pacientes: {}", JsonPrinter.toString(pacienteSalidaDto));

        return pacienteSalidaDto;
    }

    @Override
    public PacienteSalidaDto buscarPacientePorId(Long id) {
        Paciente pacienteBuscado = pacienteRepository.findById(id).orElse(null);
        PacienteSalidaDto pacienteEncontrado = null;

        if (pacienteBuscado != null) {
            pacienteEncontrado = modelMapper.map(pacienteBuscado, PacienteSalidaDto.class);
            LOGGER.info("Paciente encontrado: {}", JsonPrinter.toString(pacienteEncontrado));
        } else LOGGER.error("El id no se encuentra registrado en la base de datos");

        return pacienteEncontrado;
    }

    @Override
    public PacienteSalidaDto actualizarPaciente(PacienteModificacionEntradaDto paciente) throws ResourceNotFoundException {
        LOGGER.info("Paciente para actualizar: {}", JsonPrinter.toString(paciente));
        Paciente pacienteRecibido = modelMapper.map(paciente, Paciente.class);
        PacienteSalidaDto pacienteSalidaDto = null;

        // Guardar directamente, reemplazando todos los campos
        Optional<Paciente> pacienteEncontrado = pacienteRepository.findById(pacienteRecibido.getId());
        if (pacienteEncontrado.isPresent()) {
            pacienteRepository.save(pacienteRecibido);

            pacienteSalidaDto = modelMapper.map(pacienteRecibido, PacienteSalidaDto.class);

            LOGGER.warn("Paciente actualizado: {}", JsonPrinter.toString(pacienteSalidaDto));
        } else {
            LOGGER.error("No se encontró el paciente para actualizar");
            throw new ResourceNotFoundException("No se encontró el paciente para actualizar");
        }

        return pacienteSalidaDto;
    }

    @Override
    public void eliminarPaciente(Long id) throws ResourceNotFoundException {
        if(pacienteRepository.findById(id).orElse(null) != null) {
            pacienteRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el paciente con id: {}", id);
        } else {
            LOGGER.error("No se ha encontrado el paciente con id {}", id);
            throw new ResourceNotFoundException("No se ha encontrado el paciente con id: " + id);
        }
    }



    private void configureMapping() {
        modelMapper.typeMap(PacienteEntradaDto.class, Paciente.class).addMappings(mapper -> mapper.map(PacienteEntradaDto::getDomicilio, Paciente::setDomicilio));
        modelMapper.typeMap(Paciente.class, PacienteSalidaDto.class).addMappings(mapper -> mapper.map(Paciente::getDomicilio, PacienteSalidaDto::setDomicilio));
        modelMapper.typeMap(PacienteModificacionEntradaDto.class, Paciente.class).addMappings(mapper -> mapper.map(PacienteModificacionEntradaDto::getDomicilio, Paciente::setDomicilio));
    }
}