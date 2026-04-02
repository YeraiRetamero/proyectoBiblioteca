package org.example.dao;

import org.example.database.DatabaseConnection;
import org.example.model.Autor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AutorDAO {

    // 1. MÉTODO PARA INSERTAR (CREATE)
    public void insertar(Autor autor) {
        String sql = "INSERT INTO autores (nombre, nacionalidad) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, autor.getNombre());
            ps.setString(2, autor.getNacionalidad());
            ps.executeUpdate();
            System.out.println("Autor guardado: " + autor.getNombre());

        } catch (SQLException e) {
            System.out.println("Error al insertar autor: " + e.getMessage());
        }
    }

    // 2. MÉTODO PARA LISTAR TODOS (READ)
    public List<Autor> listarTodos() {
        List<Autor> lista = new ArrayList<>();
        String sql = "SELECT * FROM autores";

        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Autor autor = new Autor();
                // Asegúrate de que los nombres entre comillas coincidan con tu pgAdmin
                autor.setId(rs.getInt("id_autor"));
                autor.setNombre(rs.getString("nombre"));
                autor.setNacionalidad(rs.getString("nacionalidad"));
                lista.add(autor);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar autores: " + e.getMessage());
        }
        return lista;
    }

    // 3. MÉTODO PARA ELIMINAR (DELETE) - Extra para completar el CRUD
    public void eliminar(int id) {
        String sql = "DELETE FROM autores WHERE id_autor = ?";

        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Autor eliminado correctamente.");
            } else {
                System.out.println("No se encontró ningún autor con ese ID.");
            }

        } catch (SQLException e) {
            System.out.println("Error al eliminar autor: " + e.getMessage());
        }
    }
}