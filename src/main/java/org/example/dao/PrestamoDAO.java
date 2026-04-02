package org.example.dao;

import org.example.database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO {

    // 1. REGISTRAR UN PRÉSTAMO (Create)
    public void registrarPrestamo(int idLibro, int idUsuario) {
        String sql = "INSERT INTO prestamos (id_libro, id_usuario, fecha_salida, devuelto) VALUES (?, ?, CURRENT_DATE, false)";

        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idLibro);
            ps.setInt(2, idUsuario);
            ps.executeUpdate();
            System.out.println("Préstamo registrado correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al registrar préstamo: " + e.getMessage());
        }
    }

    // 2. VER PRÉSTAMOS ACTIVOS
    // Este método devuelve una lista de frases para el usuario
    public List<String> listarPrestamosDetallados() {
        List<String> lista = new ArrayList<>();

        // Unimos 3 tablas: prestamos (p), libros (l) y usuarios (u)
        String sql = "SELECT u.nombre AS usuario, l.titulo AS libro, p.fecha_salida " +
                "FROM prestamos p " +
                "JOIN usuarios u ON p.id_usuario = u.id_usuario " +
                "JOIN libros l ON p.id_libro = l.id_libro " +
                "WHERE p.devuelto = false";

        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String info = "Usuario: " + rs.getString("usuario") +
                        " | Libro: " + rs.getString("libro") +
                        " | Fecha: " + rs.getDate("fecha_salida");
                lista.add(info);
            }
        } catch (SQLException e) {
            System.out.println("Error en consulta triple JOIN: " + e.getMessage());
        }
        return lista;
    }
}