package com.abez.exCalc.model;

import java.util.HashMap;

public class Log10 implements IAction, Cloneable {
    String _variable;
    HashMap<String, Double> _variables;

    @Override
    public IAction clone() throws CloneNotSupportedException {
        return (Log10) super.clone();
    }

    @Override
    public void setArgument(String variable) {
        _variable = variable;
    }

    @Override
    public double calculate(HashMap<String, Double> variables) {
        return Math.log10(variables.get(_variable));
    }
}
