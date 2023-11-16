package com.backend.clinicaodontologica.dto.entrada.odontologo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class OdontologoEntradaDto {


    @NotNull(message = "El campo de la matrícula no puede ser nulo")
    @NotBlank(message = "Debe especificar la matrícula")
    @Size(max = 12, message = "La matrícula debe tener hasta 12 digitos")
    private String matricula;

    @NotNull(message = "El nombre no puede ser nulo")
    @NotBlank(message = "Debe especificar el nombre")
    @Size(max = 50, message = "El nombre debe tener hasta 50 caracteres")
    private String nombre;


    @NotNull(message = "El campo del apellido no puede ser nulo")
    @NotBlank(message = "Debe especificar el apellido")
    @Size(max = 50, message = "El apellido debe tener mínimo 50 caracteres")
    private String apellido;

    public OdontologoEntradaDto() {
    }

    public OdontologoEntradaDto(String matricula, String nombre, String apellido) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.apellido = apellido;
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
