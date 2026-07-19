package com.streamflow.controller;

import com.streamflow.dao.ContenidoDAO;
import com.streamflow.dao.UsuarioDAO;
import com.streamflow.model.Calidad;
import com.streamflow.model.Contenido;
import com.streamflow.model.Usuario;
import com.streamflow.service.RecomendacionService;
import com.streamflow.service.SuscripcionService;
import com.streamflow.view.StreamFlowView;

import java.util.List;

public class StreamFlowController {

    private final ContenidoDAO contenidoDAO;
    private final UsuarioDAO usuarioDAO;
    private final SuscripcionService suscripcionService;
    private final RecomendacionService recomendacionService;
    private final StreamFlowView vista;

    public StreamFlowController(ContenidoDAO contenidoDAO, UsuarioDAO usuarioDAO,
            SuscripcionService suscripcionService,
            RecomendacionService recomendacionService,
            StreamFlowView vista) {
        this.contenidoDAO = contenidoDAO;
        this.usuarioDAO = usuarioDAO;
        this.suscripcionService = suscripcionService;
        this.recomendacionService = recomendacionService;
        this.vista = vista;
    }

    public void registrarContenido(Contenido contenido) {
        contenidoDAO.crear(contenido);
        vista.mostrarMensaje("Contenido registrado: " + contenido.obtenerTitulo());
    }

    public void registrarUsuario(Usuario usuario) {
        usuarioDAO.crear(usuario);
        vista.mostrarMensaje("Usuario registrado: " + usuario.obtenerNombre());
    }

    public void listarCatalogo() {
        List<Contenido> catalogo = contenidoDAO.listarTodos();
        vista.mostrarCatalogo(catalogo);
    }

    public void reproducirContenido(int idContenido) {
        Contenido contenido = contenidoDAO.obtenerPorId(idContenido);
        if (contenido != null) {
            vista.mostrarReproduccion(contenido);
        } else {
            vista.mostrarMensaje("Contenido no encontrado");
        }
    }

    public void recomendarPorGenero(String genero) {
        List<Contenido> catalogo = contenidoDAO.listarTodos();
        List<Contenido> recomendaciones = recomendacionService.recomendarPorGenero(genero, catalogo);
        vista.mostrarRecomendaciones(genero, recomendaciones);
    }

    public double consultarCostoSuscripcion(Calidad calidad) {
        return suscripcionService.calcularCostoMensual(calidad);
    }
}
