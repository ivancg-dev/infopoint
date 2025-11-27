package biblioteca;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MainApp extends JFrame {
    private final String role;
    private final AdminPanel adminPanel = new AdminPanel();
    private final UserPanel userPanel = new UserPanel();

    public MainApp(String role) {
        super("Suite Biblioteca - " + role);
        this.role = role;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        SharedData.load();

        setLayout(new BorderLayout());
        JTabbedPane tabs = new JTabbedPane();
        tabs.setToolTipText("Cambiar entre módulos");

        if ("Admin".equals(role)) {
            tabs.addTab("Panel Admin", adminPanel);
            tabs.setMnemonicAt(0, KeyEvent.VK_A); // Alt+A para pestaña
        }
        tabs.addTab("Terminal Usuario", userPanel);
        tabs.setMnemonicAt("Admin".equals(role) ? 1 : 0, KeyEvent.VK_U);

        add(tabs, BorderLayout.CENTER);
        setJMenuBar(buildMenuBar());
    }

    private JMenuBar buildMenuBar() {
        JMenuBar mb = new JMenuBar();

        JMenu archivo = new JMenu("Archivo");
        archivo.setMnemonic(KeyEvent.VK_R); // Alt+R (A ya usado)
        JMenuItem guardar = new JMenuItem("Guardar datos");
        guardar.setMnemonic(KeyEvent.VK_G); // Alt+G
        guardar.setToolTipText("Guardar listas de Libros y Avisos a fichero");
        guardar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx())); // Ctrl+S
        guardar.addActionListener(e -> SharedData.save());

        JMenuItem cargar = new JMenuItem("Cargar datos");
        cargar.setMnemonic(KeyEvent.VK_C); // Alt+C
        cargar.setToolTipText("Cargar datos desde fichero");
        cargar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx())); // Ctrl+O
        cargar.addActionListener(e -> {
            SharedData.load();
            SwingUtilities.updateComponentTreeUI(this); // asegura repaint
        });

        JMenuItem salir = new JMenuItem("Salir");
        salir.setMnemonic(KeyEvent.VK_S);
        salir.setToolTipText("Cerrar la aplicación");
        salir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx())); // Ctrl+Q
        salir.addActionListener(e -> System.exit(0));

        archivo.add(guardar); archivo.add(cargar); archivo.addSeparator(); archivo.add(salir);

        JMenu estilo = new JMenu("Estilo");
        estilo.setMnemonic(KeyEvent.VK_E);
        JMenuItem nimbus = new JMenuItem("Nimbus");
        nimbus.setMnemonic(KeyEvent.VK_N);
        nimbus.setToolTipText("Aplicar Look & Feel Nimbus en tiempo real");
        nimbus.addActionListener(e -> setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel"));

        JMenuItem metal = new JMenuItem("Metal");
        metal.setMnemonic(KeyEvent.VK_M);
        metal.setToolTipText("Aplicar Look & Feel Metal");
        metal.addActionListener(e -> setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel"));

        JMenuItem windows = new JMenuItem("Windows");
        windows.setMnemonic(KeyEvent.VK_W);
        windows.setToolTipText("Aplicar Look & Feel Windows");
        windows.addActionListener(e -> setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"));

        JMenuItem altoContraste = new JMenuItem("Alto Contraste");
        altoContraste.setMnemonic(KeyEvent.VK_H);
        altoContraste.setToolTipText("Modo personalizado de alto contraste");
        altoContraste.addActionListener(e -> applyHighContrast());

        estilo.add(nimbus); estilo.add(metal); estilo.add(windows); estilo.addSeparator(); estilo.add(altoContraste);

        mb.add(archivo);
        mb.add(estilo);
        return mb;
    }

    private void setLookAndFeel(String lafClass) {
        try {
            UIManager.setLookAndFeel(lafClass);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "No se pudo aplicar el estilo: " + ex.getMessage(),
                    "Look & Feel", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void applyHighContrast() {
        try {
            // Simulación: colores fuertes y fuentes grandes
            UIManager.put("control", Color.BLACK);
            UIManager.put("text", Color.WHITE);
            UIManager.put("nimbusBase", Color.BLACK);
            UIManager.put("nimbusBlueGrey", Color.DARK_GRAY);
            UIManager.put("info", Color.YELLOW);
            UIManager.put("Menu.foreground", Color.WHITE);
            UIManager.put("Menu.background", Color.BLACK);
            UIManager.put("MenuItem.foreground", Color.WHITE);
            UIManager.put("MenuItem.background", Color.BLACK);
            UIManager.put("Label.foreground", Color.WHITE);
            UIManager.put("Panel.background", Color.BLACK);
            UIManager.put("OptionPane.messageForeground", Color.WHITE);
            UIManager.put("ToolTip.background", Color.BLACK);
            UIManager.put("ToolTip.foreground", Color.YELLOW);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "No se pudo activar alto contraste: " + ex.getMessage(),
                    "Accesibilidad", JOptionPane.WARNING_MESSAGE);
        }
    }
}
