package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dto.entrada.paciente.PacienteEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.TurnoModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;
import com.backend.clinicaodontologica.entity.Odontologo;
import com.backend.clinicaodontologica.entity.Paciente;
import com.backend.clinicaodontologica.entity.Turno;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaodontologica.repository.TurnoRepository;
import com.backend.clinicaodontologica.service.ITurnoService;
import com.backend.clinicaodontologica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurnoService implements ITurnoService {
    private final Logger LOGGER = LoggerFactory.getLogger(TurnoService.class);
    private final TurnoRepository turnoRepository;
    private final ModelMapper modelMapper;
    private final PacienteService pacienteService;
    private final OdontologoService odontologoService;

    public TurnoService(TurnoRepository turnoRepository, ModelMapper modelMapper, PacienteService pacienteService, OdontologoService odontologoService) {
        this.turnoRepository = turnoRepository;
        this.modelMapper = modelMapper;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
        configureMapping();
    }

    @Override
    public TurnoSalidaDto registrarTurno(TurnoEntradaDto turno) throws ResourceNotFoundException {
        LOGGER.info("TurnoEntradaDto: " + JsonPrinter.toString(turno));

        // Buscar el paciente por id
        PacienteSalidaDto pacienteDto = pacienteService.buscarPacientePorId(turno.getIdPaciente());
        if (pacienteDto == null) {
            LOGGER.error("El paciente con id " + turno.getIdPaciente() + " no existe");
            throw new ResourceNotFoundException("El paciente con id " + turno.getIdPaciente() + " no existe");
        }

        // Buscar el odontólogo por id
        OdontologoSalidaDto odontologoDto = odontologoService.buscarOdontologoPorId(turno.getIdOdontologo());
        if (odontologoDto == null) {
            LOGGER.error("El odontólogo con id " + turno.getIdOdontologo() + " no existe");
            throw new ResourceNotFoundException("El odontólogo con id " + turno.getIdOdontologo() + " no existe");
        }

        // Convertir los DTOs a entidades
        Paciente paciente = modelMapper.map(pacienteDto, Paciente.class);
        Odontologo odontologo = modelMapper.map(odontologoDto, Odontologo.class);

        // Crear un nuevo Turno con el paciente y odontólogo obtenidos
        Turno turnoEntidad = new Turno(turno.getFechaYHora(), odontologo, paciente);

        // Persistir a la capa de DAO y obtener entidad
        Turno turnoAPersistir = turnoRepository.save(turnoEntidad);

        // Convertir la entidad a TurnoSalidaDto
        TurnoSalidaDto turnoSalidaDto = modelMapper.map(turnoAPersistir, TurnoSalidaDto.class);

        LOGGER.info("TurnoSalidaDto: " + JsonPrinter.toString(turnoSalidaDto));
        return turnoSalidaDto;
    }



    @Override
    public List<TurnoSalidaDto> listarTurnos() {
        List<TurnoSalidaDto> turnoSalidaDtos = turnoRepository.findAll().stream().map(turno -> modelMapper.map(turno, TurnoSalidaDto.class)).toList();

        if (LOGGER.isInfoEnabled()) LOGGER.info("Listado de todos los turnos: {}", JsonPrinter.toString(turnoSalidaDtos));

        return turnoSalidaDtos;
    }

    @Override
    public TurnoSalidaDto buscarTurnoPorId(Long id) {
        Turno turnoBuscado = turnoRepository.findById(id).orElse(null);
        TurnoSalidaDto turnoEncontrado = null;

        if(turnoEncontrado != null) {
            turnoEncontrado = modelMapper.map(turnoBuscado, TurnoSalidaDto.class);
            LOGGER.info("Turno encontrado: {}", JsonPrinter.toString(turnoEncontrado));
        } else LOGGER.error("El id no se encuentra registrado en la base de datos");

        return turnoEncontrado;
    }

    @Override
    public TurnoSalidaDto actualizarTurno(TurnoModificacionEntradaDto turno) throws ResourceNotFoundException {
        // Buscar el turno existente por id
        Turno turnoExistente = turnoRepository.findById(turno.getId()).orElse(null);
        if (turnoExistente == null) {
            LOGGER.error("El turno con id " + turno.getId() + " no existe");
            throw new ResourceNotFoundException("El turno con id " + turno.getId() + " no existe");
        }

        // Buscar por Ids
        PacienteSalidaDto pacienteSalidaDto = pacienteService.buscarPacientePorId(turno.getIdPaciente());
        if (pacienteSalidaDto == null) {
            LOGGER.error("El paciente con id " + turno.getIdPaciente() + " no existe");
            throw new ResourceNotFoundException("El paciente con id " + turno.getIdPaciente() + " no existe");
        }

        OdontologoSalidaDto odontologoSalidaDto = odontologoService.buscarOdontologoPorId(turno.getIdOdontologo());
        if (odontologoSalidaDto == null) {
            LOGGER.error("El odontólogo con id " + turno.getIdOdontologo() + " no existe");
            throw new ResourceNotFoundException("El odontólogo con id " + turno.getIdOdontologo() + " no existe");
        }

        // Convertir los DTOs a entidades
        Paciente paciente = modelMapper.map(pacienteSalidaDto, Paciente.class);
        Odontologo odontologo = modelMapper.map(odontologoSalidaDto, Odontologo.class);

        // Actualizar la información del turno
        turnoExistente.setFechaYHora(turno.getFechaYHora());
        turnoExistente.setPaciente(paciente);
        turnoExistente.setOdontologo(odontologo);

        // Guardar el turno actualizado
        Turno turnoActualizado = turnoRepository.save(turnoExistente);
        TurnoSalidaDto turnoSalidaDto = modelMapper.map(turnoActualizado, TurnoSalidaDto.class);

        LOGGER.info("Turno actualizado: " + JsonPrinter.toString(turnoSalidaDto));
        return turnoSalidaDto;
    }

    @Override
    public void eliminarTurno(Long id) throws ResourceNotFoundException {
        if(turnoRepository.findById(id).orElse(null) != null) {
            turnoRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el turno con id: {}", id);
        } else {
            LOGGER.error("No se ha encontrado el turno con id {}", id);
            throw new ResourceNotFoundException("No se ha encontrado el turno con id: " + id);
        }
    }

    private void configureMapping() {
        /*
        // Mapeo de TurnoEntradaDto a Turno
        modelMapper.typeMap(TurnoEntradaDto.class, Turno.class)
                .addMappings(mapper -> mapper.skip(Turno::setOdontologo))
                .addMappings(mapper -> mapper.skip(Turno::setPaciente));

        // Mapeo de TurnoModificacionEntradaDto a Turno
        modelMapper.typeMap(TurnoModificacionEntradaDto.class, Turno.class)
                .addMappings(mapper -> mapper.skip(Turno::setOdontologo))
                .addMappings(mapper -> mapper.skip(Turno::setPaciente));

         */

        // Mapeo de Turno a TurnoSalidaDto
        modelMapper.typeMap(Turno.class, TurnoSalidaDto.class);
    }
}