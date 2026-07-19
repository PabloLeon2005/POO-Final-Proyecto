package com.streamflow.model;

public class Serie extends Contenido {

    private final int numeroTemporadas;

    public Serie(int id, String titulo, String genero, Calidad calidad, int numeroTemporadas) {
        super(id, titulo, genero, calidad);
        this.numeroTemporadas = numeroTemporadas;
    }

    public int obtenerNumeroTemporadas() {
        return numeroTemporadas;
    }

    @Override
    public void reproducir() {
        System.out.println("Reproduciendo serie '" + obtenerTitulo() + "' (" + numeroTemporadas
                + " temporadas) en calidad " + obtenerCalidad() + "...");
    }

    @Override
    public String obtenerDetalles() {
        return "Serie: " + obtenerTitulo() + " | Genero: " + obtenerGenero()
                + " | Temporadas: " + numeroTemporadas + " | Calidad: " + obtenerCalidad();
    }

    @Override
    public String obtenerTipo() {
        return "SERIE";
    }
}
