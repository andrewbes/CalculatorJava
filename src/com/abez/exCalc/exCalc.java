package com.abez.exCalc;

import com.abez.exCalc.ui.CalcWidgetCoordinator;
import com.abez.exCalc.services.ExpressionService;

/**
 * Implements the expression calculator for operations +, -, *, /, ^
 * applying the brackets and functions sqrt, sin, cos, tan, atan,
 * log10, log2. Also applying the use of arguments
 */
public class exCalc {
    /**
     * Gets a mathematical expression and a list of arguments as input and
     * given a double type result as terminal output
     *
     * @param args Mathematical expression in format "-sin(a*2)+1+b^(2-4)*c" and
     *             arguments in format "a=3.5" "b=5"... Space characters
     *             are allowed in any position
     */
    public static void main(String[] args)  {
        if (args.length == 0) {
            args = new String[]{"a+b"};
        }
        ExpressionService.shared.initWith(args);
        ExpressionService.shared.setVariables(args);
        ExpressionService.shared.calculate();
        var coordinator = new CalcWidgetCoordinator();
        coordinator.start();
    }


}