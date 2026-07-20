package com.streamflow.service;

import com.streamflow.model.Calidad;
import com.streamflow.model.Suscripcion;

public interface SuscripcionService {
    double calcularCostoMensual(Calidad calidad);
    Suscripcion generarSuscripcion(Calidad calidad);
}
