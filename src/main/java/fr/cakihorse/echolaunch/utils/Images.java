//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package fr.cakihorse.echolaunch.utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Images {
    public Images() {
    }

    public static JButton newButton(Image path, int width, int height, boolean isNotTransparent) {
        ImageIcon playIcon = new ImageIcon(path);
        Image resizedImage = playIcon.getImage().getScaledInstance(width, height, 4);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        JButton btn = new JButton(resizedIcon);
        btn.setBorderPainted(isNotTransparent);
        btn.setFocusPainted(isNotTransparent);
        btn.setContentAreaFilled(isNotTransparent);
        btn.setBorder(new EmptyBorder(0, 0, 0, 0));
        return btn;
    }
}
