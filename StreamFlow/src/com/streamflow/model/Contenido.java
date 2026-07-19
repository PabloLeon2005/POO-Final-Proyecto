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

    /**
     * Cada subtipo construye su propia representacion textual con la
     * informacion que le es relevante (duracion, temporadas, director, etc).
     */
    public abstract String obtenerDetalles();

    /**
     * Identificador de tipo usado exclusivamente por la capa de persistencia
     * para reconstruir el objeto concreto correcto al leer de SQLite.
     * No se usa en ningun punto de la logica de negocio (Service/Controller),
     * que siempre trabaja contra la referencia Contenido.
     */
    public abstract String obtenerTipo();
}
