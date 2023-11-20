package com.backend.clinicaodontologica.dto.entrada.turno;

import com.backend.clinicaodontologica.dto.entrada.odontologo.OdontologoEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.paciente.PacienteEntradaDto;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class TurnoEntradaDto {
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

    public TurnoEntradaDto() {}

    public TurnoEntradaDto(LocalDateTime fechaYHora, OdontologoEntradaDto odontologoEntradaDto, PacienteEntradaDto pacienteEntradaDto) {
        this.fechaYHora = fechaYHora;
        this.odontologoEntradaDto = odontologoEntradaDto;
        this.pacienteEntradaDto = pacienteEntradaDto;
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