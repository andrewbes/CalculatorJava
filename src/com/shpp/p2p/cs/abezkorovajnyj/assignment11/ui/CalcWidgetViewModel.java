package com.shpp.p2p.cs.abezkorovajnyj.assignment11.ui;

import com.shpp.p2p.cs.abezkorovajnyj.assignment11.services.ExpressionService;

public class CalcWidgetViewModel {
    public String getExpressionStr() {
        return ExpressionService.shared.getExpressionStr();
    }

    public String getResult() {
        var result = ExpressionService.shared.getResult();
        if (result != null) {
            return String.valueOf(result);
        }
        return "unavailable";
    }

    public String[] getVariablesNames() {
        String variables[] = ExpressionService.shared.getVariablesNames();
        return  variables;
    }

    public String getVariableName(int index) {
        String name = ExpressionService.shared.getVariableName(index);
        return  name;
    }

    public String getVariableValue(int index) {
       return String.valueOf(ExpressionService.shared.getVariableValue(index));
    }

    public int getVariablesCount() {
        return ExpressionService.shared.getVariablesCount();
    }
}
