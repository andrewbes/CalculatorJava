package com.shpp.p2p.cs.abezkorovajnyj.assignment11.ui.widgets;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class CalcJTextField extends JTextField {
    private Shape shape;
    private int brdColor;
    public CalcJTextField(int size) {
        super(size);
        setOpaque(false); // As suggested by @AVD in comment.
    }
    public CalcJTextField(String text, int fontSize, int color, int brdColor) {
        super(text);
        setForeground(new Color(color));
        setFont(new Font("Arial", Font.BOLD, fontSize));
        setOpaque(false); // As suggested by @AVD in comment.
        this.brdColor = brdColor;
    }
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
        super.paintComponent(g);
    }
    protected void paintBorder(Graphics g) {
        g.setColor(new Color(brdColor));
        g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
    }
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 15, 15);
        }
        return shape.contains(x, y);
    }
}
