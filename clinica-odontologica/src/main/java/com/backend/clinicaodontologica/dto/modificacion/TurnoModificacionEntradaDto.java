package com.backend.clinicaodontologica.dto.modificacion;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime fechaYHora;
    @NotNull(message = "El id odontologo no puede ser nulo")
    @Valid
    private Long idodontologo;
    @NotNull(message = "El id paciente no puede ser nulo")
    @Valid
    private Long idpaciente;

    public TurnoModificacionEntradaDto() {}

    public TurnoModificacionEntradaDto(Long id, LocalDateTime fechaYHora, Long idodontologo, Long idpaciente) {
        this.id = id;
        this.fechaYHora = fechaYHora;
        this.idodontologo = idodontologo;
        this.idpaciente = idpaciente;
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