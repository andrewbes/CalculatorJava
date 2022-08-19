package com.abez.exCalc.model;

import java.util.HashMap;

public interface IAction {

    /**
     * set the argument of the function
     *
     */
    void setArgument(String variable);

    /**
     * calculate the value of the expression
     *
     * @param variables all available variables
     */
    double calculate(HashMap<String, Double> variables);

    /**
     * creates the copy of objects
     *
     */
    IAction clone() throws CloneNotSupportedException;
}
