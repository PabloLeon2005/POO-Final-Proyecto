package com.streamflow.model;

public class Documental extends Contenido {

    private final String director;

    public Documental(int id, String titulo, String genero, Calidad calidad, String director) {
        super(id, titulo, genero, calidad);
        this.director = director;
    }

    public String obtenerDirector() {
        return director;
    }

    @Override
    public void reproducir() {
        System.out.println("Reproduciendo documental '" + obtenerTitulo() + "' dirigido por "
                + director + " en calidad " + obtenerCalidad() + "...");
    }

    @Override
    public String obtenerDetalles() {
        return "Documental: " + obtenerTitulo() + " | Genero: " + obtenerGenero()
                + " | Director: " + director + " | Calidad: " + obtenerCalidad();
    }

    @Override
    public String obtenerTipo() {
        return "DOCUMENTAL";
    }
}
