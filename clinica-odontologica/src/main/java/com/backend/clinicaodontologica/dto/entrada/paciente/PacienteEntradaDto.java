package com.backend.clinicaodontologica.dto.entrada.paciente;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;

public class PacienteEntradaDto {
    @NotNull(message = "El nombre del paciente no puede ser nulo")
    @NotBlank(message = "El nombre del paciente no puede quedar vacío")
    @Size(max = 50, message = "El nombre del paciente puede tener hasta 50 caracteres")
    private String nombre;
    @NotNull(message = "El apellido del paciente no puede ser nulo")
    @NotBlank(message = "El apellido del paciente no puede quedar vacío")
    @Size(max = 50, message = "El apellido del paciente puede tener hasta 50 caracteres")
    private String apellido;
    @NotNull(message = "El dni del paciente no puede ser nulo")
    @Digits(integer = 12, fraction = 0, message = "El dni del paciente puede tener hasta 12 dígitos")
    private Integer dni;
    @FutureOrPresent(message = "La fecha de ingreso del paciente no puede ser anterior al día de hoy")
    @NotNull(message = "La fecha de ingreso del paciente no puede ser nulo")
    //@JsonProperty("fecha_ingreso")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate fechaIngreso;
    @NotNull(message = "El domicilio del paciente no puede ser nulo")
    @Valid
    private DomicilioEntradaDto domicilio;

    public PacienteEntradaDto() {}

    public PacienteEntradaDto(String nombre, String apellido, Integer dni, LocalDate fechaIngreso, DomicilioEntradaDto domicilio) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaIngreso = fechaIngreso;
        this.domicilio = domicilio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public DomicilioEntradaDto getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(DomicilioEntradaDto domicilio) {
        this.domicilio = domicilio;
    }
}