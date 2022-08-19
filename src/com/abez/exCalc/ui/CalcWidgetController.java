package com.abez.exCalc.ui;


import com.abez.exCalc.services.ExpressionService;

import javax.swing.*;
import java.util.HashMap;

interface CalcWidgetControllerDelegate {
    void updateUI();
}

public class CalcWidgetController {


    private CalcWidgetControllerDelegate delegate;

    public void setDelegate(CalcWidgetControllerDelegate delegate) {
        this.delegate = delegate;
    }

    public void calculate(HashMap<String, JTextField> tfVariables) {
        HashMap<String, String> variables = new HashMap<>();
        for(var key: tfVariables.keySet()){
            ExpressionService.shared.setVariable(key, tfVariables.get(key).getText());
        }
        ExpressionService.shared.calculate();
        delegate.updateUI();
    }

    public void parse(String text) {
        ExpressionService.shared.initWith(text.split(","));
        delegate.updateUI();
    }
}
