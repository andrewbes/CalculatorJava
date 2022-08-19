package com.abez.exCalc.ui.widgets;

import java.awt.*;

public class Spacer extends  CalcJButton{

    public Spacer(int width, int height){
        this("",0, Color.white.getRGB(),"");
        setPreferredSize(new Dimension(width,height));
    }
    private  Spacer(String text, int fontSize, int color, String image) {
        super(text, fontSize, color, image);
    }
}
