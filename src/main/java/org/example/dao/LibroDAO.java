package org.example.dao;

import org.example.database.DatabaseConnection;
import org.example.model.Libro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {

    // 1. INSERTAR UN NUEVO LIBRO
    public void insertar(Libro libro) {
        String sql = "INSERT INTO libros (titulo, isbn, id_autor) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getIsbn());
            ps.setInt(3, libro.getIdAutor());
            ps.executeUpdate();
            System.out.println("Libro guardado: " + libro.getTitulo());

        } catch (SQLException e) {
            System.out.println("Error al insertar libro: " + e.getMessage());
        }
    }

    // 2. LISTAR LIBROS DISPONIBLES (Lógica para el Préstamo)
    // Filtra libros que NO están en la tabla préstamos con 'devuelto = false'
    public List<Libro> listarDisponibles() {
        List<Libro> lista = new ArrayList<>();
        String sql = "SELECT * FROM libros WHERE id_libro NOT IN (" +
                "SELECT id_libro FROM prestamos WHERE devuelto = false)";

        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Libro l = new Libro();
                l.setIdLibro(rs.getInt("id_libro"));
                l.setTitulo(rs.getString("titulo"));
                l.setIsbn(rs.getString("isbn"));
                l.setIdAutor(rs.getInt("id_autor"));
                lista.add(l);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar libros disponibles: " + e.getMessage());
        }
        return lista;
    }

    // 3. VER TODOS CON NOMBRES DE AUTOR (Para consulta general del Menú)
    public List<String> listarLibrosConAutor() {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT l.titulo, a.nombre AS autor " +
                "FROM libros l " +
                "JOIN autores a ON l.id_autor = a.id_autor";

        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String fila = "📖 " + rs.getString("titulo") + " | Autor: " + rs.getString("autor");
                lista.add(fila);
            }
        } catch (SQLException e) {
            System.out.println("Error en consulta JOIN: " + e.getMessage());
        }
        return lista;
    }
}