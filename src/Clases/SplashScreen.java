package Clases;

import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JDialog {

    private JProgressBar progressBar;
    private JLabel label;
    private Timer timer;
    private int progressValue = 0;

    // Mensajes que irán cambiando
    private String[] mensajes = {
        "Cargando motores...",
        "Preparando entorno...",
        "Conectando...",
        "Iniciando aplicación...",
        "Listo para comenzar..."
    };

    public SplashScreen(Frame parent) {
        super(parent, true); // modal
        initUI();
        startLoading();
    }

    private void initUI() {
        setTitle("Cargando...");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        label = new JLabel("Iniciando aplicación...", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        add(label, BorderLayout.CENTER);

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        add(progressBar, BorderLayout.SOUTH);
    }

    private void startLoading() {
        // Timer que incrementa la barra cada 100 ms
        timer = new Timer(100, e -> {
            progressValue++;
            progressBar.setValue(progressValue);

            // Cambiar mensaje cada 20%
            int index = progressValue / 20;
            if (index < mensajes.length) {
                label.setText(mensajes[index]);
            }

            if (progressValue >= 100) {
                timer.stop();
                dispose(); // cerrar el splash
            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        // Mostrar el splash
        SplashScreen splash = new SplashScreen(null);
        splash.setVisible(true);

        // Aquí ya puedes lanzar tu ventana principal
        JFrame mainFrame = new JFrame("Ventana Principal");
        mainFrame.setSize(600, 400);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
}