package com.abez.exCalc.ui;

import com.abez.exCalc.services.ExpressionService;

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
        return ExpressionService.shared.getVariablesNames();
    }

    public String getVariableName(int index) {
        return ExpressionService.shared.getVariableName(index);
    }

    public String getVariableValue(int index) {
       return String.valueOf(ExpressionService.shared.getVariableValue(index));
    }

    public int getVariablesCount() {
        return ExpressionService.shared.getVariablesCount();
    }
}
