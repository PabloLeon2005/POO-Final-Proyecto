package com.streamflow.dao;

import java.util.List;

public interface Crud<T> {
    void crear(T entidad);

    T obtenerPorId(int id);

    List<T> listarTodos();

    void actualizar(T entidad);

    void eliminar(int id);
}
