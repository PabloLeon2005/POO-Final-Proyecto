package com.streamflow.model;

import java.time.LocalDate;

public class Suscripcion {

    private int id;
    private final Calidad calidad;
    private final LocalDate fechaInicio;
    private double costoMensual;

    public Suscripcion(int id, Calidad calidad, LocalDate fechaInicio, double costoMensual) {
        this.id = id;
        this.calidad = calidad;
        this.fechaInicio = fechaInicio;
        this.costoMensual = costoMensual;
    }

    public int obtenerId() {
        return id;
    }

    public void asignarId(int id) {
        this.id = id;
    }

    public Calidad obtenerCalidad() {
        return calidad;
    }

    public LocalDate obtenerFechaInicio() {
        return fechaInicio;
    }

    public double obtenerCostoMensual() {
        return costoMensual;
    }

    public void asignarCostoMensual(double costoMensual) {
        this.costoMensual = costoMensual;
    }
}
