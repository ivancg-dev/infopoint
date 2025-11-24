package Clases;

import javax.swing.*;

public class MainApp {

    // Ventana principal (se crea después del login)
    public static MainWindow mainWindow;

    public static void main(String[] args) {

        // Siempre lanzar Swing en EventQueue
        SwingUtilities.invokeLater(() -> {
            // Mostrar login
            LoginDialog login = new LoginDialog();
            login.setVisible(true);

            // Si el login fue correcto, abrir la ventana principal
            if (login.isLoginCorrect()) {
                mainWindow = new MainWindow();
                mainWindow.setVisible(true);
            }
        });
    }
}
