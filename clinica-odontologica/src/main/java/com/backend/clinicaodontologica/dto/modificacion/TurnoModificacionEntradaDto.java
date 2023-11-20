package com.backend.clinicaodontologica.dto.modificacion;

import com.backend.clinicaodontologica.dto.entrada.odontologo.OdontologoEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.paciente.PacienteEntradaDto;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class TurnoModificacionEntradaDto {
    @NotNull(message = "El id del turno no puede ser nulo")
    private Long id;
    @FutureOrPresent(message = "La fecha y hora del turno no puede ser anterior al día de hoy")
    @NotNull(message = "La fecha y hora del turno del paciente no puede ser nulo")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDateTime fechaYHora;
    @NotNull(message = "El odontólogo del turno no puede ser nulo")
    @Valid
    private OdontologoEntradaDto odontologoEntradaDto;
    @NotNull(message = "El paciente del turno no puede ser nulo")
    @Valid
    private PacienteEntradaDto pacienteEntradaDto;

    public TurnoModificacionEntradaDto() {}

    public TurnoModificacionEntradaDto(Long id, LocalDateTime fechaYHora, OdontologoEntradaDto odontologoEntradaDto, PacienteEntradaDto pacienteEntradaDto) {
        this.id = id;
        this.fechaYHora = fechaYHora;
        this.odontologoEntradaDto = odontologoEntradaDto;
        this.pacienteEntradaDto = pacienteEntradaDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaYHora() {
        return fechaYHora;
    }

    public void setFechaYHora(LocalDateTime fechaYHora) {
        this.fechaYHora = fechaYHora;
    }

    public OdontologoEntradaDto getOdontologoEntradaDto() {
        return odontologoEntradaDto;
    }

    public void setOdontologoEntradaDto(OdontologoEntradaDto odontologoEntradaDto) {
        this.odontologoEntradaDto = odontologoEntradaDto;
    }

    public PacienteEntradaDto getPacienteEntradaDto() {
        return pacienteEntradaDto;
    }

    public void setPacienteEntradaDto(PacienteEntradaDto pacienteEntradaDto) {
        this.pacienteEntradaDto = pacienteEntradaDto;
    }
}
