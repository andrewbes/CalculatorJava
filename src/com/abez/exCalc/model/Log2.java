package com.abez.exCalc.model;

import java.util.HashMap;

public class Log2 implements IAction, Cloneable {
    String _variable;
    HashMap<String, Double> _variables;

    @Override
    public IAction clone() throws CloneNotSupportedException {
        return (Log2) super.clone();
    }

    @Override
    public void setArgument(String variable) {
        _variable = variable;
    }

    @Override
    public double calculate(HashMap<String, Double> variables) {
        return Math.log(variables.get(_variable));
    }
}
