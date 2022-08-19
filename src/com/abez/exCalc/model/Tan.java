package com.abez.exCalc.model;

import java.util.HashMap;

public class Tan implements IAction, Cloneable {
    String _variable;
    HashMap<String, Double> _variables;

    @Override
    public IAction clone() throws CloneNotSupportedException {
        return (Tan) super.clone();
    }

    @Override
    public void setArgument(String variable) {

        _variable = variable;
    }

    @Override
    public double calculate(HashMap<String, Double> variables) {
        return Math.tan(variables.get(_variable));
    }
}
