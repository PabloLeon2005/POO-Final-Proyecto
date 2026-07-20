package com.streamflow.view;

import com.streamflow.model.Contenido;
import com.streamflow.model.Usuario;

import java.util.List;

public class StreamFlowView {

    public void mostrarCatalogo(List<Contenido> catalogo) {
        System.out.println("=== Catalogo StreamFlow ===");
        for (Contenido contenido : catalogo) {
            System.out.println(contenido.obtenerDetalles());
        }
    }

    public void mostrarReproduccion(Contenido contenido) {
        contenido.reproducir();
    }

    public void mostrarUsuario(Usuario usuario) {
        System.out.println("Usuario: " + usuario.obtenerNombre()
                + " | Suscripcion: " + usuario.obtenerSuscripcion().obtenerCalidad()
                + " | Costo mensual: $" + usuario.obtenerSuscripcion().obtenerCostoMensual());
    }

    public void mostrarRecomendaciones(String genero, List<Contenido> recomendaciones) {
        System.out.println("=== Recomendaciones de genero " + genero + " ===");
        for (Contenido contenido : recomendaciones) {
            System.out.println(contenido.obtenerDetalles());
        }
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
}
