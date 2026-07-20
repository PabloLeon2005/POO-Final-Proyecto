package com.streamflow.model;

public abstract class Contenido implements Reproducible {

    private int id;
    private final String titulo;
    private final String genero;
    private final Calidad calidad;

    protected Contenido(int id, String titulo, String genero, Calidad calidad) {
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.calidad = calidad;
    }

    public int obtenerId() {
        return id;
    }

    public void asignarId(int id) {
        this.id = id;
    }

    public String obtenerTitulo() {
        return titulo;
    }

    public String obtenerGenero() {
        return genero;
    }

    public Calidad obtenerCalidad() {
        return calidad;
    }


    public abstract String obtenerDetalles();


    public abstract String obtenerTipo();
}
