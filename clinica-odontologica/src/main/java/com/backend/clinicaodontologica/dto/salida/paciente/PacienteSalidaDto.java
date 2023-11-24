package com.backend.clinicaodontologica.dto.salida.paciente;

import java.time.LocalDate;

public class PacienteSalidaDto {
    private int id;
    private String nombre;
    private String apellido;
    private int dni;
    private LocalDate fechaIngreso;
    private DomicilioSalidaDto domicilio;

    public PacienteSalidaDto() {}

    public PacienteSalidaDto(int id, String nombre, String apellido, int dni, LocalDate fechaIngreso, DomicilioSalidaDto domicilio) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaIngreso = fechaIngreso;
        this.domicilio = domicilio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public DomicilioSalidaDto getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(DomicilioSalidaDto domicilio) {
        this.domicilio = domicilio;
    }

    @Override
    public String toString() {
        return "id: " + id + " - nombre: " + nombre + " - apellido: " + apellido + " - dni: " + dni + " - fechaIngreso: " + fechaIngreso + " - domicilioSalidaDto: " + domicilio + '.';
    }
}