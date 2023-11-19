package com.backend.clinicaodontologica.dto.modificacion;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OdontologoModificacionEntradaDto {
    @NotNull(message = "El id del odontólogo no puede ser nulo")
    private Long id;
    @NotNull(message = "La matrícula del odontólogo no puede ser nulo")
    @NotBlank(message = "La matrícula del odontólogo no puede quedar vacío")
    @Size(max = 12, message = "La matrícula del odontólogo puede tener hasta 12 caracteres")
    private String matricula;
    @NotNull(message = "El nombre del odontólogo no puede ser nulo")
    @NotBlank(message = "El nombre del odontólogo no puede quedar vacío")
    @Size(max = 50, message = "El nombre del odontólogo puede tener hasta 50 caracteres")
    private String nombre;
    @NotNull(message = "El apellido del odontólogo no puede ser nulo")
    @NotBlank(message = "El apellido del odontólogo no puede quedar vacío")
    @Size(max = 50, message = "El apellido del odontólogo puede tener hasta 50 caracteres")
    private String apellido;

    public OdontologoModificacionEntradaDto() {}

    public OdontologoModificacionEntradaDto(Long id, String matricula, String nombre, String apellido) {
        this.id = id;
        this.matricula = matricula;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
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
}
