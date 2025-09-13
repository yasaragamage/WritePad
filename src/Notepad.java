import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Notepad extends JFrame {
    private JTextArea textArea;
    private File currentFile;

    public Notepad() {
        setTitle("Simple Notepad");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Text Area

        textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        //  Menu Bar

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // File Menu

        JMenu fileMenu = new JMenu("File");
        JMenuItem newItem = new JMenuItem("New");
        newItem.addActionListener(e -> newFile());
        JMenuItem openItem = new JMenuItem("Open");
        openItem.addActionListener(e -> openFile());
        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(e -> saveFile());
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);

        // Edit Menu

        JMenu editMenu = new JMenu("Edit");
        JMenuItem cutItem = new JMenuItem("Cut");
        cutItem.addActionListener(e -> textArea.cut());
        JMenuItem copyItem = new JMenuItem("Copy");
        copyItem.addActionListener(e -> textArea.copy());
        JMenuItem pasteItem = new JMenuItem("Paste");
        pasteItem.addActionListener(e -> textArea.paste());
        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);

        // Format Menu

        JMenu formatMenu = new JMenu("Format");
        JMenuItem fontItem = new JMenuItem("Font");
        fontItem.addActionListener(e -> changeFont());
        JMenuItem colorItem = new JMenuItem("Color");
        colorItem.addActionListener(e -> changeColor());
        formatMenu.add(fontItem);
        formatMenu.add(colorItem);

        //  Help Menu

        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> showAbout());
        helpMenu.add(aboutItem);

        // Add menus to bar

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(formatMenu);
        menuBar.add(helpMenu);
    }

    //  File Operations

    private void newFile() {
        textArea.setText("");
        setTitle("Untitled - Notepad");
        currentFile = null;
    }

    private void openFile() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (BufferedReader reader = new BufferedReader(new FileReader(chooser.getSelectedFile()))) {
                textArea.read(reader, null);
                currentFile = chooser.getSelectedFile();
                setTitle(currentFile.getName() + " - Notepad");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error opening file");
            }
        }
    }

    private void saveFile() {
        if (currentFile == null) {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                currentFile = chooser.getSelectedFile();
            }
        }
        if (currentFile != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentFile))) {
                textArea.write(writer);
                setTitle(currentFile.getName() + " - Notepad");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving file");
            }
        }
    }

    // Format Options

    private void changeFont() {
        String fontName = JOptionPane.showInputDialog(this, "Enter font name:", "Monospaced");
        if (fontName != null && !fontName.isEmpty()) {
            textArea.setFont(new Font(fontName, Font.PLAIN, 14));
        }
    }

    private void changeColor() {
        Color color = JColorChooser.showDialog(this, "Choose Text Color", textArea.getForeground());
        if (color != null) textArea.setForeground(color);
    }

    // Help About

    private void showAbout() {
        JOptionPane.showMessageDialog(this,
                "Simple Notepad\n\nCreated by: Yasara\nID: S17012",
                "About", JOptionPane.INFORMATION_MESSAGE);
    }

    // Main Method

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Notepad().setVisible(true));
    }
}
