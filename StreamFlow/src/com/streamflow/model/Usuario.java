package com.streamflow.model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {

    private int id;
    private final String nombre;
    private final String correo;
    private Suscripcion suscripcion;
    private final List<Contenido> favoritos;

    public Usuario(int id, String nombre, String correo, Suscripcion suscripcion) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.suscripcion = suscripcion;
        this.favoritos = new ArrayList<>();
    }

    public int obtenerId() {
        return id;
    }

    public void asignarId(int id) {
        this.id = id;
    }

    public String obtenerNombre() {
        return nombre;
    }

    public String obtenerCorreo() {
        return correo;
    }

    public Suscripcion obtenerSuscripcion() {
        return suscripcion;
    }

    public void asignarSuscripcion(Suscripcion suscripcion) {
        this.suscripcion = suscripcion;
    }

    public List<Contenido> obtenerFavoritos() {
        return favoritos;
    }


    public void agregarFavorito(Contenido contenido) {
        favoritos.add(contenido);
    }
}
