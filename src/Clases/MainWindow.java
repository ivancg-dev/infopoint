package Clases;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    public MainWindow() {
        setTitle("Suite Biblioteca - Ventana Principal");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel label = new JLabel("Bienvenido a la Biblioteca", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 22));
        add(label);
    }
}