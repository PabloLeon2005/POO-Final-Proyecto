package com.streamflow.service;

import com.streamflow.model.Contenido;

import java.util.List;

public interface RecomendacionService {
    List<Contenido> recomendarPorGenero(String genero, List<Contenido> catalogo);
}
