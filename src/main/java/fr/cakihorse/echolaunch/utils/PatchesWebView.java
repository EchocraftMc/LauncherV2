package fr.cakihorse.echolaunch.utils;

import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import java.io.IOException;

public class PatchesWebView {
    static public void newWindow(String url) {
        JFrame frame = new JFrame();
        frame.setResizable(false);
        JEditorPane editor = new JEditorPane();
        editor.setEditable(false);

        // Set the HTMLEditorKit with a custom StyleSheet
        HTMLEditorKit kit = new HTMLEditorKit();
        editor.setEditorKit(kit);

        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule("body { font-family: Arial, sans-serif; margin: 20px; }");
        styleSheet.addRule(".card { border: 1px solid #ccc; padding: 10px; margin-bottom: 10px; }");

        editor.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true);
        try {
            editor.setPage(url);
        } catch (IOException e) {
            editor.setContentType("text/html");
            editor.setText("La page n'a pas pu être chargée..");
            JOptionPane.showMessageDialog(null, "Erreur, impossible d'accéder à : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        JScrollPane scrollPane = new JScrollPane(editor);
        frame.getContentPane().add(scrollPane);
        frame.setSize(700, 400);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }
}
