package com.shpp.p2p.cs.abezkorovajnyj.assignment11.model;

import java.util.HashMap;

public class Sqrt implements IAction, Cloneable {
    String _variable;
    HashMap<String, Double> _variables;

    @Override
    public IAction clone() throws CloneNotSupportedException {
        return (Sqrt) super.clone();
    }

    @Override
    public void setArgument(String variable) {
        _variable = variable;
    }

    @Override
    public double calculate(HashMap<String, Double> variables) {
        return Math.sqrt(variables.get(_variable));
    }
}
