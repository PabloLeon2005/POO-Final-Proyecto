package com.streamflow.model;

public class Pelicula extends Contenido {

    private final int duracionMinutos;

    public Pelicula(int id, String titulo, String genero, Calidad calidad, int duracionMinutos) {
        super(id, titulo, genero, calidad);
        this.duracionMinutos = duracionMinutos;
    }

    public int obtenerDuracionMinutos() {
        return duracionMinutos;
    }

    @Override
    public void reproducir() {
        System.out.println("Reproduciendo pelicula '" + obtenerTitulo() + "' en calidad " + obtenerCalidad() + "...");
    }

    @Override
    public String obtenerDetalles() {
        return "Pelicula: " + obtenerTitulo() + " | Genero: " + obtenerGenero()
                + " | Duracion: " + duracionMinutos + " min | Calidad: " + obtenerCalidad();
    }

    @Override
    public String obtenerTipo() {
        return "PELICULA";
    }
}
