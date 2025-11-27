package biblioteca;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class LoginDialog extends JDialog {
    private final JTextField userField = new JTextField(15);
    private final JPasswordField passField = new JPasswordField(15);
    private final JComboBox<String> roleBox = new JComboBox<>(new String[]{"Admin", "Usuario"});
    private int failCount = 0;

    public LoginDialog(Frame owner) {
        super(owner, "Acceso", true);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Identificación segura"));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5,5,5,5);
        c.gridx = 0; c.gridy = 0; c.anchor = GridBagConstraints.LINE_END;
        form.add(new JLabel("Usuario:"), c);
        c.gridy++; form.add(new JLabel("Contraseña:"), c);
        c.gridy++; form.add(new JLabel("Rol:"), c);

        c.gridx = 1; c.gridy = 0; c.anchor = GridBagConstraints.LINE_START;
        userField.setToolTipText("Introduce tu usuario");
        form.add(userField, c);
        c.gridy++;
        passField.setToolTipText("Introduce tu contraseña");
        form.add(passField, c);
        c.gridy++;
        roleBox.setToolTipText("Selecciona el rol para acceder");
        form.add(roleBox, c);

        JButton ok = new JButton("Entrar");
        ok.setMnemonic(KeyEvent.VK_E); // Alt+E
        ok.setToolTipText("Validar credenciales");
        getRootPane().setDefaultButton(ok);

        JButton cancel = new JButton("Cancelar");
        cancel.setMnemonic(KeyEvent.VK_C); // Alt+C
        cancel.setToolTipText("Cerrar la aplicación");

        // Acciones
        ok.addActionListener(e -> validateLogin());
        cancel.addActionListener(e -> System.exit(0));

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(ok);
        buttons.add(cancel);

        setLayout(new BorderLayout(10,10));
        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);

        // Aceleradores globales
        setupAccelerators(ok, cancel);
    }

    private void setupAccelerators(JButton ok, JButton cancel) {
        JRootPane rp = getRootPane();
        rp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control ENTER"), "DO_OK");
        rp.getActionMap().put("DO_OK", new AbstractAction() {
            @Override public void actionPerformed(java.awt.event.ActionEvent e) { ok.doClick(); }
        });
        rp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "DO_CANCEL");
        rp.getActionMap().put("DO_CANCEL", new AbstractAction() {
            @Override public void actionPerformed(java.awt.event.ActionEvent e) { cancel.doClick(); }
        });
    }

    private void validateLogin() {
        String user = userField.getText().trim();
        String pass = new String(passField.getPassword());
        String role = (String) roleBox.getSelectedItem();

        // Regla de ejemplo: admin: admin/admin, usuario: user/user
        boolean ok = ("Admin".equals(role) && "admin".equals(user) && "admin".equals(pass))
                  || ("Usuario".equals(role) && "user".equals(user) && "user".equals(pass));

        if (ok) {
            dispose();
            SwingUtilities.invokeLater(() -> {
                MainApp app = new MainApp(role);
                app.setVisible(true);
            });
        } else {
            failCount++;
            JOptionPane.showMessageDialog(this, "Credenciales inválidas. Intentos fallidos: " + failCount,
                    "Error de acceso", JOptionPane.ERROR_MESSAGE);
            userField.setText("");
            passField.setText("");
            userField.requestFocusInWindow();
        }
    }
}
