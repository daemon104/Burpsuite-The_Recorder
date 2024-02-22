package org.example;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Test {
    public static void main(String[] args) {
        Frame frame = new Frame();
        frame.setName("suiteName");
        JPanel rootPanel = new JPanel();
        rootPanel.setBorder(new LineBorder(UIManager.getColor("FormattedTextField.selectionBackground")));
        System.out.println(String.valueOf(frame));
    }
}
