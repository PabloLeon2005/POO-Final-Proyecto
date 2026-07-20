package com.streamflow.dao;

import com.streamflow.model.Calidad;
import com.streamflow.model.Suscripcion;
import com.streamflow.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOSQLite implements UsuarioDAO {

    private final Connection conexion;

    public UsuarioDAOSQLite(ConexionSQLite conexionSQLite) {
        this.conexion = conexionSQLite.obtenerConexion();
    }

    @Override
    public void crear(Usuario usuario) {
        String sql = "INSERT INTO usuario (nombre, correo, calidad_suscripcion, costo_mensual, fecha_inicio) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, usuario.obtenerNombre());
            ps.setString(2, usuario.obtenerCorreo());
            ps.setString(3, usuario.obtenerSuscripcion().obtenerCalidad().name());
            ps.setDouble(4, usuario.obtenerSuscripcion().obtenerCostoMensual());
            ps.setString(5, usuario.obtenerSuscripcion().obtenerFechaInicio().toString());
            ps.executeUpdate();

            try (ResultSet generados = ps.getGeneratedKeys()) {
                if (generados.next()) {
                    usuario.asignarId(generados.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public Usuario obtenerPorId(int id) {
        String sql = "SELECT * FROM usuario WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener usuario: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario";
        try (Statement stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapearUsuario(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar usuarios: " + e.getMessage(), e);
        }
        return lista;
    }

    @Override
    public void actualizar(Usuario usuario) {
        String sql = "UPDATE usuario SET nombre = ?, correo = ?, calidad_suscripcion = ?, costo_mensual = ? WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, usuario.obtenerNombre());
            ps.setString(2, usuario.obtenerCorreo());
            ps.setString(3, usuario.obtenerSuscripcion().obtenerCalidad().name());
            ps.setDouble(4, usuario.obtenerSuscripcion().obtenerCostoMensual());
            ps.setInt(5, usuario.obtenerId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM usuario WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar usuario: " + e.getMessage(), e);
        }
    }

    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String nombre = rs.getString("nombre");
        String correo = rs.getString("correo");
        Calidad calidad = Calidad.valueOf(rs.getString("calidad_suscripcion"));
        double costoMensual = rs.getDouble("costo_mensual");
        LocalDate fechaInicio = LocalDate.parse(rs.getString("fecha_inicio"));

        Suscripcion suscripcion = new Suscripcion(id, calidad, fechaInicio, costoMensual);
        return new Usuario(id, nombre, correo, suscripcion);
    }
}
