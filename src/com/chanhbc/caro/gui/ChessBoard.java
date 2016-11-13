package com.chanhbc.caro.gui;

import com.chanhbc.caro.interfaces.OnChangeListener;
import com.chanhbc.caro.model.*;
import com.chanhbc.caro.model.Point;
import com.chanhbc.caro.sounds.PlayWavManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChessBoard extends BaseContainer {
    public static final int WIDTH_PANEL = ChessSquare.NUMBER_COLUMN * ChessSquare.SIZE + 1;
    public static final int HEIGHT_PANEL = ChessSquare.NUMBER_ROW * ChessSquare.SIZE + 1;

    private MouseAdapter mouseAdapter;
    private int[][] arrOCo;
    private ChessSquare chessSquare;
    private Caro caro;
    private Point point;
    private Point pointSquare;
    private int numberWin1;
    private int numberWin2;
    private static boolean isSoundEffect;
    private static int number;
    private static int playMode; // 2 là người với người, 1 là người với máy

    public ChessBoard() {
        super();
        initlizeListener();
    }

    public void setNumber(int number){
        this.number = number;
    }

    public void newArrCaro() {
        for (int i = 0; i < ChessSquare.NUMBER_ROW; i++) {
            for (int j = 0; j < ChessSquare.NUMBER_COLUMN; j++) {
                arrOCo[i][j] = 0;
            }
        }
    }

    public void setPoint() {
        point.setX(-1);
        point.setY(-1);
    }

    public static int getPlayMode() {
        return playMode;
    }

    public static void setPlayMode(int playMode) {
        ChessBoard.playMode = playMode;
    }

    private boolean checkGameWin() {
        if (caro.checkGame()) {
            if (playMode == 2) {
                if (isSoundEffect)
                    PlayWavManager.getInstance().getSoundWin().playWav();
            } else {
                if (caro.getOwn() == 1) {
                    if (isSoundEffect)
                        PlayWavManager.getInstance().getSoundWin().playWav();
                } else {
                    if (isSoundEffect)
                        PlayWavManager.getInstance().getSoundLose().playWav();
                }
            }
            JOptionPane.showMessageDialog(null, caro.getMessage(), "Game over", JOptionPane.OK_OPTION);
            int playMode1 = playMode;
            if (caro.getOwn() == 1) {
                numberWin1++;
            } else {
                numberWin2++;
            }
            int result = JOptionPane.showConfirmDialog(null, "Continue play?", "Continue", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                setPlayMode(playMode1);
                newArrCaro();
                setPoint();
            } else {
                setPlayMode(0);
            }
            return true;
        }
        return false;
    }

    private void startComputer() {
        point = caro.findMoveComputer();
        arrOCo[point.getX()][point.getY()] = 2;
        caro.setOwn(arrOCo[point.getX()][point.getY()]);
    }

    public int getNumberWin2() {
        return numberWin2;
    }

    public void setNumberWin2(int numberWin2) {
        this.numberWin2 = numberWin2;
    }

    public int getNumberWin1() {
        return numberWin1;
    }

    public void setNumberWin1(int numberWin1) {
        this.numberWin1 = numberWin1;
    }

    public boolean isSoundEffect() {
        return isSoundEffect;
    }

    public void setSoundEffect(boolean soundEffect) {
        isSoundEffect = soundEffect;
    }

    private void initlizeListener() {
        arrOCo = new int[ChessSquare.NUMBER_ROW][ChessSquare.NUMBER_COLUMN];
        mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (playMode == 0)
                    return;
                int x = e.getX();
                int y = e.getY();
                if (x % ChessSquare.SIZE == 0 || y % ChessSquare.SIZE == 0) {
                    return;
                }
                x /= ChessSquare.SIZE;
                y /= ChessSquare.SIZE;
                if (playMode == 1)
                    number = 0;
                if (arrOCo[x][y] != 0) {
                    return;
                }
                arrOCo[x][y] = 1 + (number++) % 2;
                if (isSoundEffect)
                    PlayWavManager.getInstance().getSoundClick().playWav();
                caro = new Caro(arrOCo, playMode);
                caro.setOwn(arrOCo[x][y]);
                if (checkGameWin()) {
                    return;
                }
                if (playMode == 1) {
                    startComputer();
                    checkGameWin();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                int x = e.getX();
                int y = e.getY();
                x /= ChessSquare.SIZE;
                y /= ChessSquare.SIZE;
                pointSquare.setX(x);
                pointSquare.setY(y);
            }
        };
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }

    @Override
    protected void initlizeComponent() {
        chessSquare = new ChessSquare();
        arrOCo = new int[ChessSquare.NUMBER_ROW][ChessSquare.NUMBER_COLUMN];
        for (int i = 0; i < ChessSquare.NUMBER_ROW; i++) {
            for (int j = 0; j < ChessSquare.NUMBER_COLUMN; j++) {
                arrOCo[i][j] = 0;
            }
        }
        playMode = 0;
        point = new Point(-1, -1);
        pointSquare = new Point(-1, -1);
        isSoundEffect = true;
    }

    @Override
    protected void initlizeContainer() {
        setSize(WIDTH_PANEL, HEIGHT_PANEL);
        setLayout(null);
        setBackground(new Color(173, 255, 47));
        setLocation(35, 35);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics2D.setColor(new Color(0, 128, 0));

        drawLine(graphics2D);
        graphics2D.setStroke(new BasicStroke(2));
        drawQuanCo(graphics2D);
        drawRec(graphics2D);
        repaint();
    }

    private void drawQuanCo(Graphics2D graphics2D) {
        for (int i = 0; i < ChessSquare.NUMBER_ROW; i++) {
            for (int j = 0; j < ChessSquare.NUMBER_COLUMN; j++) {
                int x = i * ChessSquare.SIZE + 1;
                int y = j * ChessSquare.SIZE + 1;
                if (arrOCo[i][j] == 1) {
                    graphics2D.setColor(Color.BLUE);
                    chessSquare.drawX(graphics2D, x, y);
                }
                if (arrOCo[i][j] == 2) {
                    graphics2D.setColor(Color.RED);
                    chessSquare.drawO(graphics2D, x, y);
                }
            }
        }
        if (point != null) {
            graphics2D.setColor(Color.MAGENTA);
            int x = point.getX() * ChessSquare.SIZE + 1;
            int y = point.getY() * ChessSquare.SIZE + 1;
            chessSquare.drawO(graphics2D, x, y);
        }


    }

    public void drawRec(Graphics2D graphics2D) {
        graphics2D.setColor(Color.YELLOW);
        getX();
        int x = pointSquare.getX() * 25;
        int y = pointSquare.getY() * 25;
        if (x >= 500)
            x = 475;
        if (y >= 500) {
            y = 475;
        }
        chessSquare.drawChessSquare(graphics2D, x, y);
    }

    private void drawLine(Graphics2D graphics2D) {
        for (int i = 0; i <= ChessSquare.NUMBER_ROW; i++) {
            graphics2D.drawLine(0, i * ChessSquare.SIZE, WIDTH_PANEL, i * ChessSquare.SIZE);

        }
        for (int i = 0; i <= ChessSquare.NUMBER_COLUMN; i++) {
            graphics2D.drawLine(i * ChessSquare.SIZE, 0, i * ChessSquare.SIZE, HEIGHT_PANEL);
        }
    }

}
