package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.TurnoModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;
import com.backend.clinicaodontologica.entity.Turno;
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

    public TurnoService(TurnoRepository turnoRepository, ModelMapper modelMapper) {
        this.turnoRepository = turnoRepository;
        this.modelMapper = modelMapper;
        configureMapping();
    }

    @Override
    public TurnoSalidaDto registrarTurno(TurnoEntradaDto turno) {
        LOGGER.info("TurnoEntradaDto: " + JsonPrinter.toString(turno));

        // Convertir de TurnoEntradaDto a Entidad por medio del mapper
        Turno turnoEntidad = modelMapper.map(turno, Turno.class);
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
    public void eliminarTurno(Long id) {
        if(turnoRepository.findById(id).orElse(null) != null) {
            turnoRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el turno con id: {}", id);
        } else {
            LOGGER.error("No se ha encontrado el turno con id {}", id);
        }
    }

    @Override
    public TurnoSalidaDto actualizarTurno(TurnoModificacionEntradaDto turno) {
        Turno turnoRecibido = modelMapper.map(turno, Turno.class);
        Turno turnoAActualizar = turnoRepository.findById(turnoRecibido.getId()).orElse(null);
        TurnoSalidaDto turnoSalidaDto = null;

        if (turnoAActualizar != null) {
            turnoAActualizar = turnoRecibido;
            turnoRepository.save(turnoAActualizar);

            turnoSalidaDto = modelMapper.map(turnoAActualizar, TurnoSalidaDto.class);

            LOGGER.warn("Turno actualizado: {}", JsonPrinter.toString(turnoSalidaDto));
        } else {
            LOGGER.error("No fue posible actualizar el turno porque no se encuentra en nuestra base de datos");
            // excepcion correspondiente
        }

        return turnoSalidaDto;
    }

    private void configureMapping() {
        modelMapper.typeMap(TurnoEntradaDto.class, Turno.class).addMappings(mapper -> {
            mapper.map(TurnoEntradaDto::getOdontologoEntradaDto, Turno::setOdontologo);
            mapper.map(TurnoEntradaDto::getPacienteEntradaDto, Turno::setOdontologo);
        });
        modelMapper.typeMap(Turno.class, TurnoSalidaDto.class).addMappings(mapper -> {
            mapper.map(Turno::getOdontologo, TurnoSalidaDto::setOdontologoSalidaDto);
            mapper.map(Turno::getPaciente, TurnoSalidaDto::setPacienteSalidaDto);
        });
        modelMapper.typeMap(TurnoModificacionEntradaDto.class, Turno.class).addMappings(mapper -> {
            mapper.map(TurnoModificacionEntradaDto::getOdontologoEntradaDto, Turno::setOdontologo);
            mapper.map(TurnoModificacionEntradaDto::getPacienteEntradaDto, Turno::setOdontologo);
        });
    }
}
