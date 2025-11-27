package biblioteca;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SplashScreenDialog splash = new SplashScreenDialog(null);
            splash.setVisible(true); // bloquea hasta completar
            // Al finalizar, el splash llama al Login
        });
    }
}
