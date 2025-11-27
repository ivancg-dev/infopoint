package biblioteca;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SplashScreenDialog extends JDialog {
    private final JProgressBar progressBar = new JProgressBar(0, 100);
    private final JLabel statusLabel = new JLabel("Inicializando...");

    public SplashScreenDialog(Frame owner) {
        super(owner, true);
        setUndecorated(true);
        setLayout(new BorderLayout(10,10));
        JPanel content = new JPanel(new BorderLayout(10,10));
        content.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Biblioteca Municipal");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        progressBar.setStringPainted(true);

        content.add(title, BorderLayout.NORTH);
        content.add(progressBar, BorderLayout.CENTER);
        content.add(statusLabel, BorderLayout.SOUTH);

        add(content, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);

        // Simulación con hilo
        new Thread(this::simulateLoad).start();
    }

    private void simulateLoad() {
        List<String> steps = List.of("Conectando...", "Cargando estilos...", "Inicializando datos...", "Preparando interfaz...");
        int stepSize = 100 / steps.size();
        int value = 0;

        for (String s : steps) {
            statusLabel.setText(s);
            for (int i = 0; i < stepSize; i++) {
                value++;
                progressBar.setValue(value);
                try { Thread.sleep(30); } catch (InterruptedException ignored) {}
            }
        }
        progressBar.setValue(100);

        SwingUtilities.invokeLater(() -> {
            dispose(); // cerrar splash
            // Abrir login
            LoginDialog login = new LoginDialog(null);
            login.setVisible(true);
            // Si login cierra con éxito, LoginDialog abrirá MainApp
        });
    }
}
