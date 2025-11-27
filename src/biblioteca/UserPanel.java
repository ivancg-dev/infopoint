package biblioteca;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.stream.Collectors;

public class UserPanel extends JPanel {
    private final JTextField searchField = new JTextField(20);
    private final JTextArea commentsArea = new JTextArea(6, 40);
    private final DefaultListModel<String> resultsModel = new DefaultListModel<>();
    private final JList<String> resultsList = new JList<>(resultsModel);
    private final UndoManager undoManager = new UndoManager();

    public UserPanel() {
        setLayout(new BorderLayout(10,10));
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        // Panel superior: búsqueda
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel searchLabel = new JLabel("Buscar:");
        searchLabel.setDisplayedMnemonic(KeyEvent.VK_B); // Alt+B
        searchLabel.setLabelFor(searchField);
        searchField.setToolTipText("Busca por título, autor o ISBN");
        JButton searchBtn = new JButton("Buscar");
        searchBtn.setMnemonic(KeyEvent.VK_U); // Alt+U
        searchBtn.setToolTipText("Ejecutar búsqueda");
        top.add(searchLabel); top.add(searchField); top.add(searchBtn);

        resultsList.setToolTipText("Resultados de búsqueda");
        JScrollPane resultsSP = new JScrollPane(resultsList);

        // Panel inferior: comentarios + undo/redo
        JPanel bottom = new JPanel(new BorderLayout(5,5));
        commentsArea.setLineWrap(true);
        commentsArea.setWrapStyleWord(true);
        commentsArea.setToolTipText("Área de comentarios del usuario (Undo/Redo disponible)");
        JScrollPane commentsSP = new JScrollPane(commentsArea);

        JPanel undoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton undoBtn = new JButton("Deshacer");
        undoBtn.setMnemonic(KeyEvent.VK_D); // Alt+D
        undoBtn.setToolTipText("Deshacer última edición (Ctrl+Z)");
        JButton redoBtn = new JButton("Rehacer");
        redoBtn.setMnemonic(KeyEvent.VK_R); // Alt+R
        redoBtn.setToolTipText("Rehacer edición (Ctrl+Y)");
        undoPanel.add(undoBtn); undoPanel.add(redoBtn);

        bottom.add(new JLabel("Comentarios:"), BorderLayout.NORTH);
        bottom.add(commentsSP, BorderLayout.CENTER);
        bottom.add(undoPanel, BorderLayout.SOUTH);

        add(top, BorderLayout.NORTH);
        add(resultsSP, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        // Acción de búsqueda
        searchBtn.addActionListener(e -> doSearch());
        getInputMap(WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("ENTER"), "DO_SEARCH");
        getActionMap().put("DO_SEARCH", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { searchBtn.doClick(); }
        });

        // Undo/Redo
        Document doc = commentsArea.getDocument();
        // CORRECCIÓN: el UndoManager se añade directamente como listener
        doc.addUndoableEditListener(undoManager);

        // Aceleradores teclado para undo/redo
        commentsArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()), "UNDO");
        commentsArea.getActionMap().put("UNDO", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                if (undoManager.canUndo()) undoManager.undo();
            }
        });
        commentsArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()), "REDO");
        commentsArea.getActionMap().put("REDO", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                if (undoManager.canRedo()) undoManager.redo();
            }
        });

        // Botones undo/redo también conectados
        undoBtn.addActionListener(e -> { if (undoManager.canUndo()) undoManager.undo(); });
        redoBtn.addActionListener(e -> { if (undoManager.canRedo()) undoManager.redo(); });
    }

    private void doSearch() {
        String q = searchField.getText().trim().toLowerCase();
        resultsModel.clear();
        if (q.isEmpty()) {
            resultsModel.addElement("Introduce un término de búsqueda.");
            return;
        }
        var resLibros = SharedData.getLibros().stream()
                .filter(l -> l.getTitulo().toLowerCase().contains(q)
                        || l.getAutor().toLowerCase().contains(q)
                        || l.getIsbn().toLowerCase().contains(q))
                .map(Libro::toString).collect(Collectors.toList());

        var resAvisos = SharedData.getAvisos().stream()
                .filter(a -> a.getTitulo().toLowerCase().contains(q)
                        || a.getContenido().toLowerCase().contains(q))
                .map(a -> "Aviso: " + a.toString()).collect(Collectors.toList());

        if (resLibros.isEmpty() && resAvisos.isEmpty()) {
            resultsModel.addElement("Sin resultados.");
        } else {
            resLibros.forEach(resultsModel::addElement);
            resAvisos.forEach(resultsModel::addElement);
        }
    }
}
