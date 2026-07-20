package com.streamflow.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class ConexionSQLite {

    private static ConexionSQLite instancia;
    private Connection conexion;

    private Conexi onSQLite(String rutaBaseDatos) {
        inicializar(rutaBaseDatos);
    }

    public static synchronized ConexionSQLite obtenerInstancia(String rutaBaseDatos) {
        if (instancia == null) {
            instancia = new ConexionSQLite(rutaBaseDatos);
        }
        return instancia;
    }

    private void inicializar(String rutaBaseDatos) {
        try {
            Class.forName("org.sqlite.JDBC");
            conexion = DriverManager.getConnection("jdbc:sqlite:" + rutaBaseDatos);
            crearTablas();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver SQLite no encontrado en el classpath: " + e.getMessage(), e);
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar con SQLite: " + e.getMessage(), e);
        }
    }

    private void crearTablas() throws SQLException {
        String sqlContenido = "CREATE TABLE IF NOT EXISTS contenido (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "titulo TEXT NOT NULL, " +
                "genero TEXT NOT NULL, " +
                "calidad TEXT NOT NULL, " +
                "tipo TEXT NOT NULL, " +
                "duracion_minutos INTEGER, " +
                "numero_temporadas INTEGER, " +
                "director TEXT)";

        String sqlUsuario = "CREATE TABLE IF NOT EXISTS usuario (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL, " +
                "correo TEXT NOT NULL, " +
                "calidad_suscripcion TEXT NOT NULL, " +
                "costo_mensual REAL NOT NULL, " +
                "fecha_inicio TEXT NOT NULL)";

        try (Statement stmt = conexion.createStatement()) {
            stmt.execute(sqlContenido);
            stmt.execute(sqlUsuario);
        }
    }

    public Connection obtenerConexion() {
        return conexion;
    }

    public void cerrar() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexion: " + e.getMessage());
        } finally {
            instancia = null;
        }
    }
}
