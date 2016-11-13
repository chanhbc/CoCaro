package com.chanhbc.caro.gui;

import com.chanhbc.caro.interfaces.OnChangeListener;
import com.chanhbc.caro.sounds.PlayWavManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuStartGame extends BaseContainer implements ActionListener {
    private OnChangeListener onChangeListener;
    private Image imgCaro = new ImageIcon(getClass().getResource("/res/images/co-caro.png")).getImage();
    private JButton btnNewGame;
    private JButton btnAbout;
    private JButton btnExit;
    private JButton btnSoundBG;
    private JButton btnSoundEffect;
    private ChessBoard chessBoard;

    public MenuStartGame() {
        PlayWavManager.getInstance().getSoundBG().loop(-1);
        PlayWavManager.getInstance().getSoundBG().playWav();
        chessBoard = new ChessBoard();
    }

    @Override
    protected void initlizeComponent() {
        btnNewGame = new JButton("New game");
        btnNewGame.setSize(200, 40);
        btnNewGame.setIcon(new ImageIcon(getClass().getResource("/res/images/newgame.png")));
        btnNewGame.setLocation(200, 400);

        btnAbout = new JButton("About");
        btnAbout.setSize(200, 40);
        btnAbout.setIcon(new ImageIcon(getClass().getResource("/res/images/about.png")));
        btnAbout.setLocation(200, 450);

        btnExit = new JButton("Exit");
        btnExit.setSize(200, 40);
        btnExit.setIcon(new ImageIcon(getClass().getResource("/res/images/exitgame.png")));
        btnExit.setLocation(300, 500);

        Font font = new Font("Lobster Two", Font.PLAIN, 22);

        btnSoundBG = new JButton("Music");
        btnSoundBG.setSize(200, 40);
        btnSoundBG.setBackground(Color.WHITE);
        btnSoundBG.setForeground(new Color(0, 122, 255));
        btnSoundBG.setFont(font);
        btnSoundBG.setIcon(new ImageIcon(getClass().getResource("/res/images/music-on.png")));
        btnSoundBG.setLocation(425, 450);

        btnSoundEffect = new JButton("Sound");
        btnSoundEffect.setSize(200, 40);
        btnSoundEffect.setBackground(Color.WHITE);
        btnSoundEffect.setForeground(new Color(0, 122, 255));
        btnSoundEffect.setFont(font);
        btnSoundEffect.setIcon(new ImageIcon(getClass().getResource("/res/images/volume-on.png")));
        btnSoundEffect.setLocation(425, 400);

        add(btnNewGame);
        add(btnAbout);
        add(btnExit);
        add(btnSoundBG);
        add(btnSoundEffect);

        btnNewGame.setActionCommand("newgame");
        btnAbout.setActionCommand("about");
        btnExit.setActionCommand("exit");
        btnSoundBG.setActionCommand("music");
        btnSoundEffect.setActionCommand("sound");
        btnNewGame.addActionListener(this);
        btnAbout.addActionListener(this);
        btnExit.addActionListener(this);
        btnSoundBG.addActionListener(this);
        btnSoundEffect.addActionListener(this);

        btnNewGame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                btnNewGame.setIcon(new ImageIcon(getClass().getResource("/res/images/newgame1.png")));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                btnNewGame.setIcon(new ImageIcon(getClass().getResource("/res/images/newgame.png")));
            }
        });

        btnAbout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                btnAbout.setIcon(new ImageIcon(getClass().getResource("/res/images/about1.png")));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                btnAbout.setIcon(new ImageIcon(getClass().getResource("/res/images/about.png")));
            }
        });

        btnExit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                btnExit.setIcon(new ImageIcon(getClass().getResource("/res/images/exitgame1.png")));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                btnExit.setIcon(new ImageIcon(getClass().getResource("/res/images/exitgame.png")));
            }
        });

        btnSoundBG.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                btnSoundBG.setBackground(new Color(255, 0, 255));
                btnSoundBG.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                btnSoundBG.setBackground(Color.WHITE);
                btnSoundBG.setForeground(new Color(0, 122, 255));
            }
        });

        btnSoundEffect.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                btnSoundEffect.setBackground(new Color(255, 0, 255));
                btnSoundEffect.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                btnSoundEffect.setBackground(Color.WHITE);
                btnSoundEffect.setForeground(new Color(0, 122, 255));
            }
        });
    }

    @Override
    protected void initlizeContainer() {
        setBackground(Color.CYAN);
        setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.drawImage(imgCaro, 50, 25, null);
        if (PlayWavManager.getInstance().getSoundBG().running()) {
            btnSoundBG.setIcon(new ImageIcon(getClass().getResource("/res/images/music-on.png")));
        } else {
            btnSoundBG.setIcon(new ImageIcon(getClass().getResource("/res/images/music-off.png")));
        }
        if(chessBoard.isSoundEffect()){
            btnSoundEffect.setIcon(new ImageIcon(getClass().getResource("/res/images/volume-on.png")));
        }else {
            btnSoundEffect.setIcon(new ImageIcon(getClass().getResource("/res/images/volume-off.png")));
        }
        repaint();
    }

    public void setOnChangeListener(OnChangeListener onChangeListener) {
        this.onChangeListener = onChangeListener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "newgame":
                onChangeListener.onChange();
                btnNewGame.setIcon(new ImageIcon(getClass().getResource("/res/images/newgame.png")));
                break;
            case "about":
                JOptionPane.showMessageDialog(null, "Game cờ caro trí tuệ nhân tạo.\nDesign by bc", "About", JOptionPane.INFORMATION_MESSAGE);
                break;
            case "music":
                if (PlayWavManager.getInstance().getSoundBG().running()) {
                    PlayWavManager.getInstance().getSoundBG().stopWav();
                    btnSoundBG.setIcon(new ImageIcon(getClass().getResource("/res/images/music-off.png")));
                } else {
                    PlayWavManager.getInstance().getSoundBG().playWav();
                    PlayWavManager.getInstance().getSoundBG().loop(-1);
                    btnSoundBG.setIcon(new ImageIcon(getClass().getResource("/res/images/music-on.png")));
                }
                break;
            case "sound":
                if (chessBoard.isSoundEffect()) {
                    System.out.println(false);
                    chessBoard.setSoundEffect(false);
                    btnSoundEffect.setIcon(new ImageIcon(getClass().getResource("/res/images/volume-off.png")));
                }else {
                    System.out.println(true);
                    chessBoard.setSoundEffect(true);
                    btnSoundEffect.setIcon(new ImageIcon(getClass().getResource("/res/images/volume-on.png")));
                }
                break;
            case "exit":
                System.exit(0);
                break;
        }
    }
}
