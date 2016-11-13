package com.chanhbc.caro.gui;

import java.awt.*;

public class ChessSquare {
    public static final int NUMBER_ROW = 20;
    public static final int NUMBER_COLUMN = 20;
    public static final int SIZE = 25;

    public void drawX(Graphics2D graphics2D, int x, int y) {
        graphics2D.drawLine(x + 4, y + 4, x + 19, y + 19);
        graphics2D.drawLine(x + 19, y + 4, x + 4, y + 19);
    }

    public void drawO(Graphics2D graphics2D, int x, int y) {
        graphics2D.drawOval(x + 3, y + 3, 17, 17);
    }

    public void drawChessSquare(Graphics2D graphics2D, int x, int y) {
        graphics2D.drawRect(x, y, SIZE, SIZE);
    }

}
