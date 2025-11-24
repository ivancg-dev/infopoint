package Clases;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class LoginDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();

    // ---- Atributos ----
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private boolean autenticado = false;

    // ---- Constructor ----
    public LoginDialog() {
        setTitle("Inicio de Sesión");
        setBounds(100, 100, 400, 220);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); 
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new GridBagLayout());

        txtUsuario = new JTextField(15);
        txtPassword = new JPasswordField(15);

        // ---- Etiqueta Usuario ----
        GridBagConstraints gbcUsuarioLabel = new GridBagConstraints();
        gbcUsuarioLabel.insets = new Insets(5, 5, 5, 5);
        gbcUsuarioLabel.anchor = GridBagConstraints.WEST;
        gbcUsuarioLabel.gridx = 0;
        gbcUsuarioLabel.gridy = 0;
        contentPanel.add(new JLabel("Usuario:"), gbcUsuarioLabel);

        // ---- Campo Usuario ----
        GridBagConstraints gbcUsuarioTxt = new GridBagConstraints();
        gbcUsuarioTxt.insets = new Insets(5, 5, 5, 5);
        gbcUsuarioTxt.anchor = GridBagConstraints.WEST;
        gbcUsuarioTxt.gridx = 1;
        gbcUsuarioTxt.gridy = 0;
        contentPanel.add(txtUsuario, gbcUsuarioTxt);

        // ---- Etiqueta Contraseña ----
        GridBagConstraints gbcPasswordLabel = new GridBagConstraints();
        gbcPasswordLabel.insets = new Insets(5, 5, 5, 5);
        gbcPasswordLabel.anchor = GridBagConstraints.WEST;
        gbcPasswordLabel.gridx = 0;
        gbcPasswordLabel.gridy = 1;
        contentPanel.add(new JLabel("Contraseña:"), gbcPasswordLabel);

        // ---- Campo Contraseña ----
        GridBagConstraints gbcPasswordTxt = new GridBagConstraints();
        gbcPasswordTxt.insets = new Insets(5, 5, 5, 5);
        gbcPasswordTxt.anchor = GridBagConstraints.WEST;
        gbcPasswordTxt.gridx = 1;
        gbcPasswordTxt.gridy = 1;
        contentPanel.add(txtPassword, gbcPasswordTxt);

        // ---- Panel de botones ----
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton btnIniciar = new JButton("Iniciar Sesión");
        buttonPane.add(btnIniciar);
        getRootPane().setDefaultButton(btnIniciar); // Enter activa este botón

        JButton btnRegistro = new JButton("Registrarse");
        buttonPane.add(btnRegistro);

        // ---- Acción del botón Iniciar Sesión----
        btnIniciar.addActionListener(e -> {
            if (verificarLogin()) {
                autenticado = true;
                dispose(); 
            } else {
                JOptionPane.showMessageDialog(this,
                        "Usuario o contraseña incorrectos",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    // ---- Método de verificación de usuario/contraseña ----
    private boolean verificarLogin() {
        String usuario = txtUsuario.getText();
        String password = new String(txtPassword.getPassword());

        // TODO Cambiarlo para utilizarlo con la base de datos
        return usuario.equals("admin") && password.equals("1234");
    }

    // ---- Método público para saber si se autenticó ----
    public boolean isLoginCorrect() {
        return autenticado;
    }
}
