package com.streamflow.model;

public enum Calidad {
    SD(5.99),
    HD(9.99),
    UHD_4K(15.99);

    private final double costoBase;

    Calidad(double costoBase) {
        this.costoBase = costoBase;
    }

    public double obtenerCostoBase() {
        return costoBase;
    }
}
