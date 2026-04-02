package org.example.model;

public class Libro {
    private int idLibro;
    private String titulo;
    private String isbn;
    private int idAutor; // La FK que conecta con la tabla autores

    public Libro() {}
    public Libro(int idLibro, String titulo, String isbn, int idAutor) {
        this.idLibro = idLibro;
        this.titulo = titulo;
        this.isbn = isbn;
        this.idAutor = idAutor;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getIdAutor() {
        return idAutor;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "titulo='" + titulo + '\'' +
                ", idAutor=" + idAutor +
                '}';
    }
}