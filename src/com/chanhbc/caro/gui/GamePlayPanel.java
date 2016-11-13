package com.chanhbc.caro.gui;

import com.chanhbc.caro.interfaces.OnChangeListener;
import com.chanhbc.caro.sounds.PlayWavManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class GamePlayPanel extends BaseContainer implements ActionListener {
    private ChessBoard chessBoard;
    private Image IMG_BG = new ImageIcon(getClass().getResource("/res/images/backgroud.jpg")).getImage();
    private Image IMG_Caro = new ImageIcon(getClass().getResource("/res/images/caro.png")).getImage();
    private JButton btnHelp;
    public JButton btnPvP;
    public JButton btnPvC;
    private JButton btnExit;
    private JButton btnSoundBG;
    private JButton btnSoundEffect;
    private OnChangeListener onChangeListener;
    private String name;
    private String name2;
    private JLabel lblPlaying;

    public GamePlayPanel() {
        super();
        System.out.println();
        initlizeListener();
    }

    private void initlizeListener() {
        btnPvP.setActionCommand("pvp");
        btnPvC.setActionCommand("pvc");
        btnHelp.setActionCommand("help");
        btnExit.setActionCommand("exit");
        btnSoundEffect.setActionCommand("sound");
        btnSoundBG.setActionCommand("music");
        btnPvC.addActionListener(this);
        btnPvP.addActionListener(this);
        btnHelp.addActionListener(this);
        btnSoundBG.addActionListener(this);
        btnSoundEffect.addActionListener(this);
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "Exit?", "Exit", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    chessBoard.newArrCaro();
                    chessBoard.setPlayMode(0);
                    chessBoard.setNumberWin1(0);
                    chessBoard.setNumberWin2(0);
                    chessBoard.setPoint();
                    lblPlaying.setText("");
                    onChangeListener.onChange();
                }
            }
        });
    }

    @Override
    protected void initlizeComponent() {
        chessBoard = new ChessBoard();
        add(chessBoard);
        Font font = new Font("Lobster Two", Font.PLAIN, 18);

        btnSoundBG = new JButton();
        btnSoundBG.setSize(40, 40);
        btnSoundBG.setIcon(new ImageIcon(getClass().getResource("/res/images/music-on.png")));
        btnSoundBG.setLocation(600, 250);

        btnSoundEffect = new JButton();
        btnSoundEffect.setSize(40, 40);
        btnSoundEffect.setIcon(new ImageIcon(getClass().getResource("/res/images/volume-on.png")));
        btnSoundEffect.setLocation(650, 250);


        btnHelp = new JButton("Help");
        btnHelp.setSize(200, 40);
        btnHelp.setIcon(new ImageIcon(getClass().getResource("/res/images/help.png")));
        btnHelp.setBorderPainted(false);
        btnHelp.setLocation(550, 450);

        btnPvP = new JButton("Player vs Player");
        btnPvP.setSize(200, 40);
        btnPvP.setIcon(new ImageIcon(getClass().getResource("/res/images/pvp.png")));
        btnPvP.setLocation(550, 350);

        btnPvC = new JButton("Player vs Computer");
        btnPvC.setSize(200, 40);
        btnPvC.setIcon(new ImageIcon(getClass().getResource("/res/images/pvc.png")));
        btnPvC.setLocation(550, 400);

        btnExit = new JButton("Exit");
        btnExit.setSize(200, 40);
        btnExit.setIcon(new ImageIcon(getClass().getResource("/res/images/exit.png")));
        btnExit.setLocation(550, 500);

        btnPvP.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                btnPvP.setIcon(new ImageIcon(getClass().getResource("/res/images/pvp1.png")));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                btnPvP.setIcon(new ImageIcon(getClass().getResource("/res/images/pvp.png")));
            }
        });

        btnPvC.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                btnPvC.setIcon(new ImageIcon(getClass().getResource("/res/images/pvc1.png")));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                btnPvC.setIcon(new ImageIcon(getClass().getResource("/res/images/pvc.png")));
            }
        });

        btnHelp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                btnHelp.setIcon(new ImageIcon(getClass().getResource("/res/images/help1.png")));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                btnHelp.setIcon(new ImageIcon(getClass().getResource("/res/images/help.png")));
            }
        });
        btnExit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                btnExit.setIcon(new ImageIcon(getClass().getResource("/res/images/exit1.png")));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                btnExit.setIcon(new ImageIcon(getClass().getResource("/res/images/exit.png")));
            }
        });

        lblPlaying = new JLabel("");
        lblPlaying.setSize(200, 50);
        lblPlaying.setFont(font);
        lblPlaying.setForeground(Color.RED);
        lblPlaying.setLocation(550, 300);
        add(lblPlaying);

        add(btnPvP);
        add(btnPvC);
        add(btnHelp);
        add(btnExit);
        add(btnSoundBG);
        add(btnSoundEffect);
    }

    @Override
    protected void initlizeContainer() {
        setSize(GUI.WIDTH_FRAME, GUI.HEIGHT_FRAME);
        setLayout(null);
        setBackground(Color.WHITE);
        name = "player";
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.drawImage(IMG_BG, 0, 0, 800, 600, null);
        graphics2D.drawImage(IMG_Caro, 550, 35, 200, 200, null);
        if (chessBoard.getPlayMode() == 1)
            lblPlaying.setText(name + ": " + chessBoard.getNumberWin1() + "      Computer: " + chessBoard.getNumberWin2());
        else if (chessBoard.getPlayMode() == 2)
            lblPlaying.setText(name + ": " + chessBoard.getNumberWin1() + "      " + name2 + ": " + chessBoard.getNumberWin2());
        if (PlayWavManager.getInstance().getSoundBG().running()) {
            btnSoundBG.setIcon(new ImageIcon(getClass().getResource("/res/images/music-on.png")));
        } else {
            btnSoundBG.setIcon(new ImageIcon(getClass().getResource("/res/images/music-off.png")));
        }
        if (chessBoard.isSoundEffect()) {
            btnSoundEffect.setIcon(new ImageIcon(getClass().getResource("/res/images/volume-on.png")));
        } else {
            btnSoundEffect.setIcon(new ImageIcon(getClass().getResource("/res/images/volume-off.png")));
        }
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "pvp":
                int result = JOptionPane.showConfirmDialog(null, "Bạn có muốn chơi mới không?", "New Game", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    name = JOptionPane.showInputDialog(null, "Player 1?", "What your name?", JOptionPane.QUESTION_MESSAGE);
                    name2 = JOptionPane.showInputDialog(null, "Player 2?", "What your name?", JOptionPane.QUESTION_MESSAGE);
                    if (name != null && name2 != null) {
                        chessBoard.setPlayMode(2);
                        chessBoard.newArrCaro();
                        chessBoard.setPoint();
                        chessBoard.setNumberWin1(0);
                        chessBoard.setNumberWin2(0);
                        chessBoard.setNumber(0);
                        if (name.length() == 0)
                            name = "Player 1";
                        if (name2.length() == 0)
                            name2 = "Player 2";
                        lblPlaying.setText(name + ": " + chessBoard.getNumberWin1() + "      " + name2 + ": " + chessBoard.getNumberWin2());
                        if (chessBoard.isSoundEffect())
                            PlayWavManager.getInstance().getSoundStart().playWav();
                    }
                }
                break;
            case "pvc":
                int result1 = JOptionPane.showConfirmDialog(null, "Bạn có muốn chơi mới không?", "New Game", JOptionPane.YES_NO_OPTION);
                if (result1 == JOptionPane.YES_OPTION) {
                    name = JOptionPane.showInputDialog(null, "What your name?", "What your name?", JOptionPane.QUESTION_MESSAGE);
                    if (name != null) {
                        chessBoard.setPlayMode(1);
                        chessBoard.newArrCaro();
                        chessBoard.setPoint();
                        chessBoard.setNumberWin1(0);
                        chessBoard.setNumberWin2(0);
                        if (name.length() == 0) {
                            name = "Player";
                        }
                        lblPlaying.setText(name + ": " + chessBoard.getNumberWin1() + "      Computer: " + chessBoard.getNumberWin2());
                        if (chessBoard.isSoundEffect())
                            PlayWavManager.getInstance().getSoundStart().playWav();
                    }
                }
                break;
            case "help":
                String mes = "- X đi trước, O đi sau." +
                        "\n- Bên nào có 5 quân cùng màu xếp thành hàng dọc, hàng ngang, đường chéo trước thì bên đấy thắng.";
                JOptionPane.showMessageDialog(null, mes, "Help", JOptionPane.INFORMATION_MESSAGE);
                break;
            case "sound":
                if (chessBoard.isSoundEffect()) {
                    chessBoard.setSoundEffect(false);
                    btnSoundEffect.setIcon(new ImageIcon(getClass().getResource("/res/images/volume-off.png")));
                } else {
                    chessBoard.setSoundEffect(true);
                    btnSoundEffect.setIcon(new ImageIcon(getClass().getResource("/res/images/volume-on.png")));
                }
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
            default:
                break;
        }
    }

    public void setOnChangeListener(OnChangeListener onChangeListener) {
        this.onChangeListener = onChangeListener;
    }
}
