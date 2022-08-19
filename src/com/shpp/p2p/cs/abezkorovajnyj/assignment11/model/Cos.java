package com.shpp.p2p.cs.abezkorovajnyj.assignment11.model;

import java.util.HashMap;

public class Cos implements IAction, Cloneable {
    String _variable;
    HashMap<String, Double> _variables;

    @Override
    public IAction clone() throws CloneNotSupportedException {
        return (Cos) super.clone();
    }

    @Override
    public void setArgument(String variable) {
        _variable = variable;
    }

    @Override
    public double calculate(HashMap<String, Double> variables) {
        return Math.cos(variables.get(_variable));
    }
}
