package org.example.main;

import org.example.dao.*;
import org.example.model.*;
import java.util.Scanner;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Scanner sn = new Scanner(System.in);

        // Inicializamos todos los DAOs
        AutorDAO autorDAO = new AutorDAO();
        LibroDAO libroDAO = new LibroDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        PrestamoDAO prestamoDAO = new PrestamoDAO();

        boolean salir = false;
        int opcion;

        while (!salir) {
            System.out.println("\n==========================================");
            System.out.println("     SISTEMA DE GESTIÓN BIBLIOTECARIA    ");
            System.out.println("==========================================");
            System.out.println("1. Listar todos los Autores");
            System.out.println("2. Añadir un nuevo Autor");
            System.out.println("3. Ver Catálogo de Libros (JOIN)");
            System.out.println("4. Registrar un nuevo Usuario");
            System.out.println("5. Listar todos los Usuarios");
            System.out.println("6. REALIZAR UN PRÉSTAMO (Solo disponibles)");
            System.out.println("7. VER PRÉSTAMOS ACTIVOS (Triple JOIN)");
            System.out.println("8. SALIR");
            System.out.print("Selecciona una opción: ");

            opcion = sn.nextInt();
            sn.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    System.out.println("\n--- LISTA DE AUTORES ---");
                    autorDAO.listarTodos().forEach(a ->
                            System.out.println("ID: " + a.getId() + " | " + a.getNombre() + " (" + a.getNacionalidad() + ")"));
                    break;

                case 2:
                    System.out.print("Nombre del autor: ");
                    String nAutor = sn.nextLine();
                    System.out.print("Nacionalidad: ");
                    String nac = sn.nextLine();
                    autorDAO.insertar(new Autor(0, nAutor, nac));
                    break;

                case 3:
                    System.out.println("\n--- CATÁLOGO COMPLETO ---");
                    libroDAO.listarLibrosConAutor().forEach(System.out::println);
                    break;

                case 4:
                    System.out.print("Nombre del usuario: ");
                    String nUsu = sn.nextLine();
                    System.out.print("Email: ");
                    String email = sn.nextLine();
                    System.out.print("Teléfono: ");
                    String tlf = sn.nextLine();
                    usuarioDAO.insertar(new Usuario(0, nUsu, email, tlf));
                    break;

                case 5:
                    System.out.println("\n--- LISTA DE USUARIOS ---");
                    usuarioDAO.listarTodos().forEach(u ->
                            System.out.println("ID: " + u.getIdUsuario() + " | " + u.getNombre()));
                    break;

                case 6:
                    System.out.println("\n--- NUEVO PRÉSTAMO ---");

                    // PASO 1: Elegir Libro Disponible
                    List<Libro> disponibles = libroDAO.listarDisponibles();
                    if (disponibles.isEmpty()) {
                        System.out.println("No hay libros disponibles. Todos están prestados.");
                        break;
                    }
                    System.out.println("Selecciona el libro:");
                    for (int i = 0; i < disponibles.size(); i++) {
                        System.out.println((i + 1) + ". " + disponibles.get(i).getTitulo());
                    }
                    System.out.print("Elige número de libro: ");
                    int selLibro = sn.nextInt() - 1;

                    // PASO 2: Elegir Usuario
                    List<Usuario> usuarios = usuarioDAO.listarTodos();
                    if (usuarios.isEmpty()) {
                        System.out.println("No hay usuarios registrados.");
                        break;
                    }
                    System.out.println("\nSelecciona el usuario:");
                    for (int i = 0; i < usuarios.size(); i++) {
                        System.out.println((i + 1) + ". " + usuarios.get(i).getNombre());
                    }
                    System.out.print("Elige número de usuario: ");
                    int selUsu = sn.nextInt() - 1;

                    // PASO 3: Ejecutar Préstamo
                    try {
                        int idLibroReal = disponibles.get(selLibro).getIdLibro();
                        int idUsuarioReal = usuarios.get(selUsu).getIdUsuario();
                        prestamoDAO.registrarPrestamo(idLibroReal, idUsuarioReal);
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Error: Selección fuera de rango.");
                    }
                    break;

                case 7:
                    System.out.println("\n--- RELACIÓN DE PRÉSTAMOS ACTUALES ---");
                    List<String> prestamosActivos = prestamoDAO.listarPrestamosDetallados();
                    if (prestamosActivos.isEmpty()) {
                        System.out.println("No hay préstamos registrados.");
                    } else {
                        prestamosActivos.forEach(System.out::println);
                    }
                    break;

                case 8:
                    salir = true;
                    System.out.println("Cerrando el sistema... ¡Buen día!");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
        }
        sn.close();
    }
}