package com.backend.clinicaodontologica.dto.modificacion;

import com.backend.clinicaodontologica.dto.salida.paciente.DomicilioSalidaDto;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class PacienteModificacionEntradaDto {
    @NotNull(message = "El id del paciente no puede ser nulo")
    private Long id;
    @NotNull(message = "El nombre del paciente no puede ser nulo")
    @NotBlank(message = "El nombre del paciente no puede quedar vacío")
    @Size(max = 50, message = "El nombre del paciente puede tener hasta 50 caracteres")
    private String nombre;
    @NotNull(message = "El apellido del paciente no puede ser nulo")
    @NotBlank(message = "El apellido del paciente no puede quedar vacío")
    @Size(max = 50, message = "El apellido del paciente puede tener hasta 50 caracteres")
    private String apellido;
    @NotNull(message = "El dni del paciente no puede ser nulo")
    @Size(max = 12, message = "El dni del paciente puede tener hasta 12 digitos")
    private Integer dni;
    @FutureOrPresent(message = "La fecha de ingreso del paciente no puede ser anterior al día de hoy")
    @NotNull(message = "La fecha de ingreso del paciente no puede ser nulo")
    //@JsonProperty("fecha_ingreso")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate fechaIngreso;
    @NotNull(message = "El domicilio del paciente no puede ser nulo")
    @Valid
    private DomicilioSalidaDto domicilioSalidaDto;

    public PacienteModificacionEntradaDto() {}

    public PacienteModificacionEntradaDto(Long id, String nombre, String apellido, Integer dni, LocalDate fechaIngreso, DomicilioSalidaDto domicilioSalidaDto) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaIngreso = fechaIngreso;
        this.domicilioSalidaDto = domicilioSalidaDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public DomicilioSalidaDto getDomicilioSalidaDto() {
        return domicilioSalidaDto;
    }

    public void setDomicilioSalidaDto(DomicilioSalidaDto domicilioSalidaDto) {
        this.domicilioSalidaDto = domicilioSalidaDto;
    }
}
