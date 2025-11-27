package biblioteca;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyEvent;

public class AdminPanel extends JPanel {
    private final DefaultListModel<Libro> librosModel = new DefaultListModel<>();
    private final JList<Libro> librosList = new JList<>(librosModel);

    private final DefaultListModel<Aviso> avisosModel = new DefaultListModel<>();
    private final JList<Aviso> avisosList = new JList<>(avisosModel);

    public AdminPanel() {
        setLayout(new BorderLayout(10,10));
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(buildForms(), BorderLayout.CENTER);
        add(buildLists(), BorderLayout.EAST);

        // Inicializar con datos compartidos
        SharedData.getLibros().forEach(librosModel::addElement);
        SharedData.getAvisos().forEach(avisosModel::addElement);
    }

    private JComponent buildForms() {
        JPanel forms = new JPanel(new GridLayout(2,1,10,10));

        // Panel Libros
        JPanel librosPanel = new JPanel(new GridBagLayout());
        librosPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Gestión de Libros", TitledBorder.LEFT, TitledBorder.TOP));
        JTextField titulo = new JTextField(15); titulo.setToolTipText("Título del libro");
        JTextField autor = new JTextField(15); autor.setToolTipText("Autor del libro");
        JTextField anio  = new JTextField(6);  anio.setToolTipText("Año (numérico)");
        JTextField isbn  = new JTextField(12); isbn.setToolTipText("ISBN (texto)");

        JButton addLibro = new JButton("Agregar Libro");
        addLibro.setMnemonic(KeyEvent.VK_L); // Alt+L
        addLibro.setToolTipText("Crear un nuevo libro con los datos indicados");
        getInputMap(WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("ENTER"), "DEFAULT_ADD_LIBRO");
        getActionMap().put("DEFAULT_ADD_LIBRO", new AbstractAction() {
            @Override public void actionPerformed(java.awt.event.ActionEvent e) { addLibro.doClick(); }
        });

        GridBagConstraints c = gbc();
        librosPanel.add(new JLabel("Título:"), lbl(c,0,0));
        librosPanel.add(titulo, fld(c,1,0));
        librosPanel.add(new JLabel("Autor:"), lbl(c,0,1));
        librosPanel.add(autor, fld(c,1,1));
        librosPanel.add(new JLabel("Año:"), lbl(c,0,2));
        librosPanel.add(anio, fld(c,1,2));
        librosPanel.add(new JLabel("ISBN:"), lbl(c,0,3));
        librosPanel.add(isbn, fld(c,1,3));
        c.gridx=1; c.gridy=4; c.anchor=GridBagConstraints.LINE_START;
        librosPanel.add(addLibro, c);

        addLibro.addActionListener(e -> {
            try {
                String t = titulo.getText().trim();
                String a = autor.getText().trim();
                String i = isbn.getText().trim();
                if (t.isEmpty() || a.isEmpty() || i.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Todos los campos excepto año son obligatorios.",
                            "Validación", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int anioNum = parseIntStrict(anio.getText().trim(), "Año");
                Libro libro = new Libro(t, a, anioNum, i);
                SharedData.getLibros().add(libro);
                librosModel.addElement(libro);
                clearFields(titulo, autor, anio, isbn);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Validación numérica", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Panel Avisos
        JPanel avisosPanel = new JPanel(new GridBagLayout());
        avisosPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Gestión de Avisos", TitledBorder.LEFT, TitledBorder.TOP));
        JTextField tituloA = new JTextField(15); tituloA.setToolTipText("Título del aviso");
        JTextArea contenidoA = new JTextArea(4, 20); contenidoA.setLineWrap(true); contenidoA.setWrapStyleWord(true);
        contenidoA.setToolTipText("Contenido del aviso");
        JButton addAviso = new JButton("Publicar Aviso");
        addAviso.setMnemonic(KeyEvent.VK_P); // Alt+P
        addAviso.setToolTipText("Crear un nuevo aviso");
        JScrollPane sp = new JScrollPane(contenidoA);

        GridBagConstraints c2 = gbc();
        avisosPanel.add(new JLabel("Título:"), lbl(c2,0,0));
        avisosPanel.add(tituloA, fld(c2,1,0));
        c2.gridx=0; c2.gridy=1; c2.anchor=GridBagConstraints.FIRST_LINE_END;
        avisosPanel.add(new JLabel("Contenido:"), c2);
        c2.gridx=1; c2.gridy=1; c2.fill=GridBagConstraints.BOTH; c2.weightx=1; c2.weighty=1;
        avisosPanel.add(sp, c2);
        c2.gridx=1; c2.gridy=2; c2.fill=GridBagConstraints.NONE; c2.weighty=0; c2.anchor=GridBagConstraints.LINE_START;
        avisosPanel.add(addAviso, c2);

        addAviso.addActionListener(e -> {
            String t = tituloA.getText().trim();
            String ctt = contenidoA.getText().trim();
            if (t.isEmpty() || ctt.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Título y contenido son obligatorios.",
                        "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Aviso aviso = new Aviso(t, ctt);
            SharedData.getAvisos().add(aviso);
            avisosModel.addElement(aviso);
            clearFields(tituloA); contenidoA.setText("");
        });

        forms.add(librosPanel);
        forms.add(avisosPanel);
        return forms;
    }

    private JComponent buildLists() {
        JPanel lists = new JPanel(new GridLayout(2,1,10,10));
        JPanel lPanel = new JPanel(new BorderLayout());
        lPanel.setBorder(BorderFactory.createTitledBorder("Libros"));
        librosList.setToolTipText("Lista de libros registrados");
        lPanel.add(new JScrollPane(librosList), BorderLayout.CENTER);

        JPanel aPanel = new JPanel(new BorderLayout());
        aPanel.setBorder(BorderFactory.createTitledBorder("Avisos"));
        avisosList.setToolTipText("Lista de avisos publicados");
        aPanel.add(new JScrollPane(avisosList), BorderLayout.CENTER);

        lists.add(lPanel); lists.add(aPanel);
        return lists;
    }

    private GridBagConstraints gbc() {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5,5,5,5);
        c.anchor = GridBagConstraints.LINE_END;
        return c;
    }
    private GridBagConstraints lbl(GridBagConstraints c, int x, int y) {
        GridBagConstraints nc = (GridBagConstraints) c.clone();
        nc.gridx = x; nc.gridy = y; nc.anchor = GridBagConstraints.LINE_END;
        return nc;
    }
    private GridBagConstraints fld(GridBagConstraints c, int x, int y) {
        GridBagConstraints nc = (GridBagConstraints) c.clone();
        nc.gridx = x; nc.gridy = y; nc.anchor = GridBagConstraints.LINE_START;
        return nc;
    }

    private int parseIntStrict(String text, String fieldName) {
        if (text.isEmpty()) return 0;
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("El campo '" + fieldName + "' debe ser numérico. Valor recibido: '" + text + "'.");
        }
    }

    private void clearFields(JTextField... fields) {
        for (JTextField f : fields) f.setText("");
        if (fields.length > 0) fields[0].requestFocusInWindow();
    }
}
