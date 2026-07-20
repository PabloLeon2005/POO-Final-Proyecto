package com.streamflow.dao;

import com.streamflow.model.Calidad;
import com.streamflow.model.Contenido;
import com.streamflow.model.Documental;
import com.streamflow.model.Pelicula;
import com.streamflow.model.Serie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class ContenidoDAOSQLite implements ContenidoDAO {

    private final Connection conexion;

    public ContenidoDAOSQLite(ConexionSQLite conexionSQLite) {
        this.conexion = conexionSQLite.obtenerConexion();
    }

    @Override
    public void crear(Contenido contenido) {
        String sql = "INSERT INTO contenido (titulo, genero, calidad, tipo, duracion_minutos, numero_temporadas, director) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, contenido.obtenerTitulo());
            ps.setString(2, contenido.obtenerGenero());
            ps.setString(3, contenido.obtenerCalidad().name());
            ps.setString(4, contenido.obtenerTipo());

            if (contenido instanceof Pelicula pelicula) {
                ps.setInt(5, pelicula.obtenerDuracionMinutos());
                ps.setNull(6, Types.INTEGER);
                ps.setNull(7, Types.VARCHAR);
            } else if (contenido instanceof Serie serie) {
                ps.setNull(5, Types.INTEGER);
                ps.setInt(6, serie.obtenerNumeroTemporadas());
                ps.setNull(7, Types.VARCHAR);
            } else if (contenido instanceof Documental documental) {
                ps.setNull(5, Types.INTEGER);
                ps.setNull(6, Types.INTEGER);
                ps.setString(7, documental.obtenerDirector());
            }

            ps.executeUpdate();

            try (ResultSet generados = ps.getGeneratedKeys()) {
                if (generados.next()) {
                    contenido.asignarId(generados.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear contenido: " + e.getMessage(), e);
        }
    }

    @Override
    public Contenido obtenerPorId(int id) {
        String sql = "SELECT * FROM contenido WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearContenido(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener contenido: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Contenido> listarTodos() {
        List<Contenido> lista = new ArrayList<>();
        String sql = "SELECT * FROM contenido";
        try (Statement stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapearContenido(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar contenido: " + e.getMessage(), e);
        }
        return lista;
    }

    @Override
    public void actualizar(Contenido contenido) {
        String sql = "UPDATE contenido SET titulo = ?, genero = ?, calidad = ? WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, contenido.obtenerTitulo());
            ps.setString(2, contenido.obtenerGenero());
            ps.setString(3, contenido.obtenerCalidad().name());
            ps.setInt(4, contenido.obtenerId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar contenido: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM contenido WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar contenido: " + e.getMessage(), e);
        }
    }

    private Contenido mapearContenido(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String titulo = rs.getString("titulo");
        String genero = rs.getString("genero");
        Calidad calidad = Calidad.valueOf(rs.getString("calidad"));
        String tipo = rs.getString("tipo");

        return switch (tipo) {
            case "PELICULA" -> new Pelicula(id, titulo, genero, calidad, rs.getInt("duracion_minutos"));
            case "SERIE" -> new Serie(id, titulo, genero, calidad, rs.getInt("numero_temporadas"));
            case "DOCUMENTAL" -> new Documental(id, titulo, genero, calidad, rs.getString("director"));
            default -> throw new IllegalStateException("Tipo de contenido desconocido: " + tipo);
        };
    }
}
