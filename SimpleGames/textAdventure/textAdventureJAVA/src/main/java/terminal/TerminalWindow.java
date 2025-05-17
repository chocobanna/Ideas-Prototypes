// src/main/java/terminal/TerminalWindow.java
package terminal;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;

public class TerminalWindow extends JFrame {
    private final JTextArea textArea = new JTextArea();
    private final CommandHandler handler = new CommandHandler();
    private final DocumentFilter filter = new DocumentFilter() {
        @Override public void insertString(FilterBypass fb, int off, String str, AttributeSet attr)
                throws BadLocationException {
            if (off >= promptPosition) super.insertString(fb, off, str, attr);
            else Toolkit.getDefaultToolkit().beep();
        }
        @Override public void remove(FilterBypass fb, int off, int len)
                throws BadLocationException {
            if (off >= promptPosition) super.remove(fb, off, len);
            else Toolkit.getDefaultToolkit().beep();
        }
        @Override public void replace(FilterBypass fb, int off, int len, String txt, AttributeSet attrs)
                throws BadLocationException {
            if (off >= promptPosition) super.replace(fb, off, len, txt, attrs);
            else Toolkit.getDefaultToolkit().beep();
        }
    };
    private int promptPosition = 0;

    public TerminalWindow() {
        super("Terminal");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initUI();
        setSize(800, 600);
        setLocationRelativeTo(null);
        appendPrompt();
        setVisible(true);
        textArea.requestFocusInWindow();
    }

    private void initUI() {
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.GREEN);
        textArea.setCaretColor(Color.GREEN);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        // install filter on initial document
        ((AbstractDocument) textArea.getDocument()).setDocumentFilter(filter);

        textArea.addKeyListener(new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) {
                if (textArea.getCaretPosition() < promptPosition)
                    textArea.setCaretPosition(textArea.getDocument().getLength());

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume();
                    String cmd = textArea.getText().substring(promptPosition).trim();
                    if ("clear".equals(cmd)) {
                        // completely wipe and re-install filter
                        PlainDocument newDoc = new PlainDocument();
                        newDoc.setDocumentFilter(filter);
                        textArea.setDocument(newDoc);
                        promptPosition = 0;
                        appendPrompt();
                        return;
                    }

                    appendText("\n");
                    String resp = handler.handle(cmd);
                    if (resp != null) appendText(resp + "\n");
                    appendPrompt();
                }
            }
        });

        add(new JScrollPane(textArea), BorderLayout.CENTER);
    }

    private void appendPrompt() {
        appendText(handler.getCurrentDirPath() + "> ");
    }

    private void appendText(String text) {
        try {
            textArea.getDocument().insertString(
                textArea.getDocument().getLength(), text, null
            );
        } catch (BadLocationException ignored) {}
        promptPosition = textArea.getDocument().getLength();
        textArea.setCaretPosition(promptPosition);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TerminalWindow::new);
    }
}
