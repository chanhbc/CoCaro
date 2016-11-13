package com.chanhbc.caro.gui;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    public static final int WIDTH_FRAME = 800;
    public static final int HEIGHT_FRAME = 600;

    private GamePlayPanel gamePlayPanel;
    private MyContainer myContainer;

    public GUI() {
        initlize();
        initlizeComponent();
    }

    private void initlizeComponent() {
        myContainer = new MyContainer();
        add(myContainer);
    }

    private void initlize() {
        setTitle("Cờ caro");
        setSize(WIDTH_FRAME, HEIGHT_FRAME);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new CardLayout());
    }
}
