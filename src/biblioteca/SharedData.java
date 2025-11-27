package biblioteca;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class SharedData {
    private static final List<Libro> libros = new ArrayList<>();
    private static final List<Aviso> avisos = new ArrayList<>();
    private static final File dataFile = new File("biblioteca.dat");

    public static List<Libro> getLibros() { return libros; }
    public static List<Aviso> getAvisos() { return avisos; }

    // Persistencia simple por serialización
    public static void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dataFile))) {
            oos.writeObject(libros);
            oos.writeObject(avisos);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar datos: " + e.getMessage(),
                    "Persistencia", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    public static void load() {
        if (!dataFile.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dataFile))) {
            libros.clear(); avisos.clear();
            libros.addAll((List<Libro>) ois.readObject());
            avisos.addAll((List<Aviso>) ois.readObject());
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar datos: " + e.getMessage(),
                    "Persistencia", JOptionPane.ERROR_MESSAGE);
        }
    }
}
