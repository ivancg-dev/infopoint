package biblioteca;

import java.io.Serializable;

public class Libro implements Serializable {
    private String titulo;
    private String autor;
    private int anio;
    private String isbn;

    public Libro(String titulo, String autor, int anio, String isbn) {
        this.titulo = titulo; this.autor = autor; this.anio = anio; this.isbn = isbn;
    }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public int getAnio() { return anio; }
    public String getIsbn() { return isbn; }
    public void setTitulo(String t) { this.titulo = t; }
    public void setAutor(String a) { this.autor = a; }
    public void setAnio(int a) { this.anio = a; }
    public void setIsbn(String i) { this.isbn = i; }

    @Override public String toString() { return titulo + " (" + autor + ", " + anio + ") - " + isbn; }
}
