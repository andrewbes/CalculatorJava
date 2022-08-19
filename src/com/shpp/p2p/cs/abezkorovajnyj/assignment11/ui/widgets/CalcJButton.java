package com.shpp.p2p.cs.abezkorovajnyj.assignment11.ui.widgets;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class CalcJButton extends JButton {

    public CalcJButton (String text, int fontSize, int color, Border br) {
        this(text,fontSize, color, "", null, true);
    }
    public CalcJButton (String text, int fontSize, int color, String image) {
        this(text,fontSize, color, image, null, false);
    }
    public CalcJButton (String text, int fontSize, int color, String image, Border br, Boolean isBorder){
        super(text);
        setBorder(br);
        setBorderPainted(isBorder);
        setContentAreaFilled(false);
        setOpaque(false);
        setFocusable(false);
        setForeground(new Color(color));
        setFont(new Font("Arial", Font.PLAIN, fontSize));
        try {
            ImageIcon img = new ImageIcon(image);
            setIcon(img);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        setPreferredSize(new Dimension(100,30));
    }
}
