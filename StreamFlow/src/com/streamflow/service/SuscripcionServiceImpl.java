package com.streamflow.service;

import com.streamflow.model.Calidad;
import com.streamflow.model.Suscripcion;

import java.time.LocalDate;

public class SuscripcionServiceImpl implements SuscripcionService {

    @Override
    public double calcularCostoMensual(Calidad calidad) {
        if (calidad == null) {
            throw new IllegalArgumentException("La calidad no puede ser nula");
        }
        return calidad.obtenerCostoBase();
    }

    @Override
    public Suscripcion generarSuscripcion(Calidad calidad) {
        double costo = calcularCostoMensual(calidad);
        return new Suscripcion(0, calidad, LocalDate.now(), costo);
    }
}
