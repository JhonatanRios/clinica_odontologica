package com.backend.clinicaodontologica.dto.entrada.turno;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class TurnoEntradaDto {
    @FutureOrPresent(message = "La fecha y hora del turno no puede ser anterior al día de hoy")
    @NotNull(message = "La fecha y hora del turno del paciente no puede ser nulo")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDateTime fechaYHora;
    @NotNull(message = "El id del odontólogo del turno no puede ser nulo")
    private Long idOdontologo;
    @NotNull(message = "El id del paciente del turno no puede ser nulo")
    private Long idPaciente;

    public TurnoEntradaDto() {}

    public TurnoEntradaDto(LocalDateTime fechaYHora, Long idOdontologo, Long idPaciente) {
        this.fechaYHora = fechaYHora;
        this.idOdontologo = idOdontologo;
        this.idPaciente = idPaciente;
    }

    public LocalDateTime getFechaYHora() {
        return fechaYHora;
    }

    public void setFechaYHora(LocalDateTime fechaYHora) {
        this.fechaYHora = fechaYHora;
    }

    public Long getIdOdontologo() {
        return idOdontologo;
    }

    public void setIdOdontologo(Long idOdontologo) {
        this.idOdontologo = idOdontologo;
    }

    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }
}