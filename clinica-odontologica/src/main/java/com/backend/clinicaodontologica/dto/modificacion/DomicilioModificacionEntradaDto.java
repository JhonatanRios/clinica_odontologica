package com.backend.clinicaodontologica.dto.modificacion;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class DomicilioModificacionEntradaDto {
    @NotNull(message = "El id del domicilo no puede ser nulo")
    private Long id;
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

    public DomicilioModificacionEntradaDto() {}

    public DomicilioModificacionEntradaDto(Long id, String calle, Integer numero, String localidad, String provincia) {
        this.id = id;
        this.calle = calle;
        this.numero = numero;
        this.localidad = localidad;
        this.provincia = provincia;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
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