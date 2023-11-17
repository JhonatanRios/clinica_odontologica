package com.backend.clinicaodontologica.dto.entrada.paciente;

import javax.validation.constraints.*;

public class DomicilioEntradaDto {
    @NotNull(message = "La calle del domicilo no puede ser nulo")
    @NotBlank(message = "La calle del domicilo no puede quedar vacío")
    private String calle;

    @NotNull(message = "El numero del domicilo no puede ser nulo")
    @Digits(integer = 8, fraction = 0, message = "El número del domicilo debe tener como máximo 8 dígitos")
    //@Pattern(regexp = "\\d{1,8}", message = "El número debe tener como máximo 8 dígitos")
    private Integer numero;

    @NotNull(message = "La localidad del domicilo no puede ser nulo")
    @NotBlank(message = "La localidad del domicilo no puede quedar vacío")
    private String localidad;

    @NotNull(message = "La provincia del domicilo no puede ser nulo")
    @NotBlank(message = "La provincia del domicilo no puede quedar vacío")
    private String provincia;

    public DomicilioEntradaDto() {
    }
    public DomicilioEntradaDto(String calle, int numero, String localidad, String provincia) {
        this.calle = calle;
        this.numero = numero;
        this.localidad = localidad;
        this.provincia = provincia;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
}