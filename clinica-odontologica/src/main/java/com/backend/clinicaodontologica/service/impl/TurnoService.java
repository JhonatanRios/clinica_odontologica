package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.TurnoModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;
import com.backend.clinicaodontologica.entity.Odontologo;
import com.backend.clinicaodontologica.entity.Paciente;
import com.backend.clinicaodontologica.entity.Turno;
import com.backend.clinicaodontologica.exceptions.BadRequestException;
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
    public TurnoSalidaDto registrarTurno(TurnoEntradaDto turno) throws BadRequestException {
        LOGGER.info("TurnoEntradaDto: " + JsonPrinter.toString(turno));
        TurnoSalidaDto turnoSalidaDto = null;

        PacienteSalidaDto turnoPacienteAgendado = pacienteService.buscarPacientePorId(turno.getIdpaciente());
        OdontologoSalidaDto turnoOdontologoAgendado = odontologoService.buscarOdontologoPorId(turno.getIdodontologo());

        Turno turnoEntidad = modelMapper.map(turno, Turno.class);

        if (turnoPacienteAgendado != null && turnoOdontologoAgendado != null) {
            // Asignar los resultados de las búsquedas a turnoEntidad
            turnoEntidad.setPaciente(modelMapper.map(turnoPacienteAgendado, Paciente.class));
            turnoEntidad.setOdontologo(modelMapper.map(turnoOdontologoAgendado, Odontologo.class));

            Turno turnoAPersistir = turnoRepository.save(turnoEntidad);
            LOGGER.info("turnoAPersistir: {}", JsonPrinter.toString(turnoAPersistir));
            turnoSalidaDto = modelMapper.map(turnoAPersistir, TurnoSalidaDto.class);
        } else if (turnoPacienteAgendado != null && turnoOdontologoAgendado == null) {
            throw new BadRequestException("No se encontró al odontólogo con el ID: " + turno.getIdodontologo());
        } else if (turnoPacienteAgendado == null && turnoOdontologoAgendado != null) {
            throw new BadRequestException("No se encontró al paciente con el ID: " + turno.getIdpaciente());
        } else throw new BadRequestException("No se encontró ni al odontólogo ni al paciente");

        LOGGER.info("TurnoSalida: {}", JsonPrinter.toString(turnoSalidaDto));
        return turnoSalidaDto;
    }

    @Override
    public List<TurnoSalidaDto> listarTurnos() {
        List<TurnoSalidaDto> listaTurnos = turnoRepository.findAll().stream().map(turno -> modelMapper.map(turno, TurnoSalidaDto.class)).toList();

        if (LOGGER.isInfoEnabled()) LOGGER.info("Listado de todos los turnos: {}", JsonPrinter.toString(listaTurnos));

        return listaTurnos;
    }

    @Override
    public TurnoSalidaDto buscarTurnoPorId(Long id) {
        Turno turnoBuscado = turnoRepository.findById(id).orElse(null);
        TurnoSalidaDto turnoEncontrado = null;

        if (turnoBuscado != null) {
            turnoEncontrado = modelMapper.map(turnoBuscado, TurnoSalidaDto.class);
            LOGGER.info("Turno encontrado: {}", JsonPrinter.toString(turnoEncontrado));
        } else LOGGER.error("El id no se encuentra registrado en la base de datos");

        return turnoEncontrado;
    }

    @Override
    public TurnoSalidaDto actualizarTurno(TurnoModificacionEntradaDto turno) throws ResourceNotFoundException {
        return null;
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
        modelMapper.typeMap(TurnoEntradaDto.class, Turno.class);

        // Mapeo de Turno a TurnoSalidaDto
        modelMapper.createTypeMap(Turno.class, TurnoSalidaDto.class)
                .addMapping(src -> src.getPaciente(), TurnoSalidaDto::setPaciente)
                .addMapping(src -> src.getOdontologo(), TurnoSalidaDto::setOdontologo)
                .addMapping(src -> src.getFechaYHora(), TurnoSalidaDto::setFechaYHora);
    }
}