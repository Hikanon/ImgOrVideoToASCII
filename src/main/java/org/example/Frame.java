package org.example;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame{

    public Frame(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        ImageConverter imageConverter = new ImageConverter();
        setSize(800, 600);
        JTextArea textArea = new JTextArea(imageConverter.convertToASCII("sourceImage.png", 4).toString(), imageConverter.getWorkingImage().getHeight(), imageConverter.getWorkingImage().getWidth());
        textArea.setFont(new Font("Monospaced", Font.BOLD, 5));
        textArea.setEditable(false);
        add(new JScrollPane(textArea));
        setResizable(true);
        setVisible(true);
    }
}
