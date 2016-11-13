package com.chanhbc.caro.gui;

import javax.swing.*;

public abstract class BaseContainer extends JPanel{
    public BaseContainer(){
        initlizeContainer();
        initlizeComponent();
    }

    protected abstract void initlizeComponent();

    protected abstract void initlizeContainer();
}
