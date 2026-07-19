package com.streamflow;

import com.streamflow.controller.StreamFlowController;
import com.streamflow.dao.ConexionSQLite;
import com.streamflow.dao.ContenidoDAO;
import com.streamflow.dao.ContenidoDAOSQLite;
import com.streamflow.dao.UsuarioDAO;
import com.streamflow.dao.UsuarioDAOSQLite;
import com.streamflow.model.Calidad;
import com.streamflow.model.Documental;
import com.streamflow.model.Pelicula;
import com.streamflow.model.Serie;
import com.streamflow.model.Suscripcion;
import com.streamflow.model.Usuario;
import com.streamflow.service.RecomendacionService;
import com.streamflow.service.RecomendacionServiceImpl;
import com.streamflow.service.SuscripcionService;
import com.streamflow.service.SuscripcionServiceImpl;
import com.streamflow.view.StreamFlowView;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ConexionSQLite conexionSQLite = ConexionSQLite.obtenerInstancia("streamflow.db");

        ContenidoDAO contenidoDAO = new ContenidoDAOSQLite(conexionSQLite);
        UsuarioDAO usuarioDAO = new UsuarioDAOSQLite(conexionSQLite);
        SuscripcionService suscripcionService = new SuscripcionServiceImpl();
        RecomendacionService recomendacionService = new RecomendacionServiceImpl();
        StreamFlowView vista = new StreamFlowView();

        StreamFlowController controlador = new StreamFlowController(
                contenidoDAO, usuarioDAO, suscripcionService, recomendacionService, vista);

        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        System.out.println("=== Bienvenido a StreamFlow ===");

        while (!salir) {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1. Registrar nueva Película");
            System.out.println("2. Registrar nueva Serie");
            System.out.println("3. Registrar nuevo Documental");
            System.out.println("4. Registrar nuevo Usuario (con suscripción)");
            System.out.println("5. Ver Catálogo de Contenido");
            System.out.println("6. Recomendar Contenido por Género");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion;
            try {
                opcion = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor, ingrese un número válido.");
                continue;
            }

            switch (opcion) {
                case 1 -> registrarPelicula(scanner, controlador);
                case 2 -> registrarSerie(scanner, controlador);
                case 3 -> registrarDocumental(scanner, controlador);
                case 4 -> registrarUsuario(scanner, controlador, suscripcionService);
                case 5 -> controlador.listarCatalogo();
                case 6 -> recomendarPorGenero(scanner, controlador);
                case 7 -> {
                    salir = true;
                    System.out.println("Saliendo del sistema. ¡Gracias por usar StreamFlow!");
                }
                default -> System.out.println("Opción no válida. Intente de nuevo.");
            }
        }

        conexionSQLite.cerrar();
        scanner.close();
    }

    private static void registrarPelicula(Scanner scanner, StreamFlowController controlador) {
        System.out.println("\n--- REGISTRAR PELÍCULA ---");
        System.out.print("Título: ");
        String titulo = scanner.nextLine().trim();
        System.out.print("Género: ");
        String genero = scanner.nextLine().trim();
        Calidad calidad = leerCalidad(scanner);
        
        System.out.print("Duración (minutos): ");
        int duracion = leerEntero(scanner);

        controlador.registrarContenido(new Pelicula(0, titulo, genero, calidad, duracion));
    }

    private static void registrarSerie(Scanner scanner, StreamFlowController controlador) {
        System.out.println("\n--- REGISTRAR SERIE ---");
        System.out.print("Título: ");
        String titulo = scanner.nextLine().trim();
        System.out.print("Género: ");
        String genero = scanner.nextLine().trim();
        Calidad calidad = leerCalidad(scanner);

        System.out.print("Número de Temporadas: ");
        int temporadas = leerEntero(scanner);

        controlador.registrarContenido(new Serie(0, titulo, genero, calidad, temporadas));
    }

    private static void registrarDocumental(Scanner scanner, StreamFlowController controlador) {
        System.out.println("\n--- REGISTRAR DOCUMENTAL ---");
        System.out.print("Título: ");
        String titulo = scanner.nextLine().trim();
        System.out.print("Género: ");
        String genero = scanner.nextLine().trim();
        Calidad calidad = leerCalidad(scanner);

        System.out.print("Director: ");
        String director = scanner.nextLine().trim();

        controlador.registrarContenido(new Documental(0, titulo, genero, calidad, director));
    }

    private static void registrarUsuario(Scanner scanner, StreamFlowController controlador, SuscripcionService suscripcionService) {
        System.out.println("\n--- REGISTRAR USUARIO ---");
        System.out.print("Nombre completo: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Correo electrónico: ");
        String correo = scanner.nextLine().trim();
        System.out.println("Seleccione la calidad de su suscripción:");
        Calidad calidad = leerCalidad(scanner);

        Suscripcion suscripcion = suscripcionService.generarSuscripcion(calidad);
        Usuario usuario = new Usuario(0, nombre, correo, suscripcion);
        controlador.registrarUsuario(usuario);
    }

    private static void recomendarPorGenero(Scanner scanner, StreamFlowController controlador) {
        System.out.println("\n--- RECOMENDACIONES ---");
        System.out.print("Ingrese el género a buscar: ");
        String genero = scanner.nextLine().trim();
        controlador.recomendarPorGenero(genero);
    }

    private static Calidad leerCalidad(Scanner scanner) {
        while (true) {
            System.out.println("Calidades disponibles: ");
            System.out.println("1. SD ($5.99)");
            System.out.println("2. HD ($9.99)");
            System.out.println("3. UHD_4K ($15.99)");
            System.out.print("Seleccione una opción: ");
            
            try {
                int opcion = Integer.parseInt(scanner.nextLine().trim());
                switch (opcion) {
                    case 1 -> { return Calidad.SD; }
                    case 2 -> { return Calidad.HD; }
                    case 3 -> { return Calidad.UHD_4K; }
                    default -> System.out.println("Opción no válida. Intente de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor, ingrese un número (1-3).");
            }
        }
    }

    private static int leerEntero(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Error: Ingrese un número entero válido: ");
            }
        }
    }
}
