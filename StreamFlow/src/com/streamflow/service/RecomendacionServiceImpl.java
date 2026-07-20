package com.streamflow.service;

import com.streamflow.model.Contenido;

import java.util.List;
import java.util.stream.Collectors;

public class RecomendacionServiceImpl implements RecomendacionService {

    @Override
    public List<Contenido> recomendarPorGenero(String genero, List<Contenido> catalogo) {
        return catalogo.stream()
                .filter(c -> c.obtenerGenero().equalsIgnoreCase(genero))
                .collect(Collectors.toList());
    }
}
