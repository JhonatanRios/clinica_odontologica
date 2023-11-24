package com.backend.clinicaodontologica.dto.entrada.turno;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class TurnoEntradaDto {
    @FutureOrPresent(message = "La fecha y hora del turno no puede ser anterior al día de hoy")
    @NotNull(message = "La fecha y hora del turno del paciente no puede ser nulo")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime fechaYHora;

    @NotNull(message = "El id odontologo no puede ser nulo")
    private Long idodontologo;
    @NotNull(message = "El id paciente no puede ser nulo")
    private Long idpaciente;

    public TurnoEntradaDto() {}

    public TurnoEntradaDto(LocalDateTime fechaYHora, Long idodontologo, Long idpaciente) {
        this.fechaYHora = fechaYHora;
        this.idodontologo = idodontologo;
        this.idpaciente = idpaciente;
    }

    public LocalDateTime getFechaYHora() {
        return fechaYHora;
    }

    public void setFechaYHora(LocalDateTime fechaYHora) {
        this.fechaYHora = fechaYHora;
    }

    public Long getIdodontologo() {
        return idodontologo;
    }

    public void setIdodontologo(Long idodontologo) {
        this.idodontologo = idodontologo;
    }

    public Long getIdpaciente() {
        return idpaciente;
    }

    public void setIdpaciente(Long idpaciente) {
        this.idpaciente = idpaciente;
    }
}