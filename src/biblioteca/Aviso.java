package biblioteca;

import java.io.Serializable;

public class Aviso implements Serializable {
    private String titulo;
    private String contenido;
    public Aviso(String titulo, String contenido) {
        this.titulo = titulo; this.contenido = contenido;
    }
    public String getTitulo() { return titulo; }
    public String getContenido() { return contenido; }
    public void setTitulo(String t) { this.titulo = t; }
    public void setContenido(String c) { this.contenido = c; }
    @Override public String toString() { return "[" + titulo + "] " + contenido; }
}
