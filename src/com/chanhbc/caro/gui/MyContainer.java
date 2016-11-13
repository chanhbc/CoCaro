package com.chanhbc.caro.gui;

import com.chanhbc.caro.interfaces.OnChangeListener;

import java.awt.*;

public class MyContainer extends BaseContainer implements OnChangeListener {
    private MenuStartGame menuStartGame;
    private MenuStartGame menuStartGame2;
    private GamePlayPanel gamePlayPanel;

    public MyContainer() {
        super();
    }

    @Override
    protected void initlizeComponent() {
        menuStartGame = new MenuStartGame();
        gamePlayPanel = new GamePlayPanel();
        menuStartGame.setOnChangeListener(this);
        gamePlayPanel.setOnChangeListener(this);
        add(menuStartGame);
    }

    @Override
    protected void initlizeContainer() {
        setLayout(new CardLayout());
    }

    @Override
    public void onChange() {
        if (menuStartGame.isShowing()) {
            remove(menuStartGame);
            add(gamePlayPanel);
        } else if (gamePlayPanel != null) {
            remove(gamePlayPanel);
            add(menuStartGame);
        }
        revalidate();
        repaint();
    }
}
