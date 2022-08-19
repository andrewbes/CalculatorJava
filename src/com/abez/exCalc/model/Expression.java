package com.abez.exCalc.model;

import java.util.*;

public class Expression implements IAction {

    private static final HashSet<Character> OPERATIONS = new HashSet<>(Arrays.asList('+', '*', '^'));
    private static final int[] FUNC_NAME_LENGTH = {2, 3, 4};
    private static final int FUNC_MAX_NAME_LENGTH = 4;

    private static final HashMap<String, IAction> FUNCTIONS_LIST =
            new HashMap<>() {{
                put("sqrt", new Sqrt());
                put("sin", new Sin());
                put("cos", new Cos());
                put("tn", new Tan());
                put("atn", new Atan());
                put("lg", new Log10());
                put("ln", new Log2());
            }};

    private static final Set<String> FUNCTIONS =  FUNCTIONS_LIST.keySet();

    ArrayList<String> _sPower;
    ArrayList<String> _sMulti;
    List<String> _sSum;
    HashMap<String, Double> _variables;
    private final HashMap<String, IAction> _childExpressions = new HashMap<>();



    private String _formula;
    private final String _ExpressionStr;
    public String getExpressionStr() {
        return _ExpressionStr;
    }

    public Expression(String formula) throws CloneNotSupportedException {
        _ExpressionStr = formula;
        _formula = formula;
        parse();
    }

    /**
     * parsing the string representation of the expression
     */
    public void parse() throws CloneNotSupportedException {
        extractBrackets();
        convertMinusToSum();
        convertDegreesToMultiplication();
        extractFunctions();
        _sPower = extractOperation('^');
        _sMulti = extractOperation('*');
        _sSum = extractSum();
    }

    /**
     * Replace the function with the name and number.
     * For same function created new object from the map of IAction
     */
    private void extractFunctions() throws CloneNotSupportedException {
        StringBuilder str = new StringBuilder(_formula);
        int funcNumber = 0;
        int minus;
        String sign;
        for (int j = 0; j < _formula.length() - FUNC_MAX_NAME_LENGTH; j++) {
            for (int len : FUNC_NAME_LENGTH) {
                String next = _formula.charAt(j) + getNextNumber(j);
                if (next.charAt(0) == '-') {
                    minus = 1;
                    sign = "-";
                } else {
                    minus = 0;
                    sign = "";
                }
                String funcName = next.substring(minus, Math.min(next.length(), len));
                String funcArgument = next.substring(minus + funcName.length());
                if (FUNCTIONS.contains(funcName)) {
                    funcNumber++;
                    String funcVariable = "fn" + funcNumber;
                    str.replace(j, j + next.length(), funcVariable);
                    _formula = str.toString();
                    IAction func;
                    func = FUNCTIONS_LIST.get(funcName).clone();
                    func.setArgument(funcArgument);
                    _childExpressions.put(sign + funcVariable, func);
                }
            }
        }
    }

    /**
     * Replaces the expression in the bracket with the name and number recursively.
     * For subexpression created new object
     */
    private void extractBrackets() throws CloneNotSupportedException {
        int openBracketsCount = 0;
        int closeBracketsCount = 0;
        int begin = 0;
        int end;
        String bracketsName;
        StringBuilder str = new StringBuilder(_formula);
        int bracketsNumber = 0;
        for (int i = 0; i < _formula.length(); i++) {
            if (_formula.charAt(i) == '(') {
                openBracketsCount++;
                if (openBracketsCount == 1) {
                    begin = i;
                }
            }
            if (str.charAt(i) == ')') {
                closeBracketsCount++;
                if (closeBracketsCount == openBracketsCount) {
                    end = i;
                    bracketsNumber++;
                    bracketsName = "br" + bracketsNumber;
                    str.replace(begin, end + 1, bracketsName);
                    i = begin;
                    openBracketsCount = 0;
                    closeBracketsCount = 0;
                    IAction expression = new Expression(_formula.substring(begin + 1, end));
                    _childExpressions.put(bracketsName, expression);
                    _formula = str.toString();
                }
            }
        }
    }

    /**
     * Converts a line with "+" signs to a line with a "," delimiter
     *
     * @return line with a "," delimiter
     */
    private List<String> extractSum() {
        StringBuilder str = new StringBuilder(_formula);
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '+') {
                str.setCharAt(i, ',');
            }
        }
        return Arrays.asList(str.toString().split(","));
    }

    /**
     * Adds "+" signs in the middle of the line before "-" signs
     */
    private void convertMinusToSum() {
        StringBuilder str = new StringBuilder();
        str.append(_formula.charAt(0));
        for (int i = 1; i < _formula.length(); i++) {
            if (_formula.charAt(i) == '-') {
                str.append("+");
            }
            str.append(_formula.charAt(i));
        }
        _formula = str.toString();
    }

    /**
     * Adds "*1" signs in the middle of the line before "/" signs
     */
    private void convertDegreesToMultiplication() {
        StringBuilder str = new StringBuilder();
 //       System.out.println(_formula);
        for (int i = 0; i < _formula.length(); i++) {
            if (_formula.charAt(i) == '/') {
                str.append("*1");
            }
            str.append(_formula.charAt(i));
        }
        _formula = str.toString();
//        System.out.println(_formula);
    }

    /**
     * Replaces the operation in the argument line with the name and number of the operation
     *
     * @param operation sign of the operation
     * @return string array of the same operations
     */
    private ArrayList<String> extractOperation(char operation) {
        String opName = getOperationsName(operation);
        StringBuilder str = new StringBuilder(_formula);
        ArrayList<String> strOperation = new ArrayList<>();
        int operationsNumber = 0;
        for (int i = 0; i < _formula.length(); i++) {
            if (_formula.charAt(i) == operation) {
                operationsNumber++;
                String begin = getPreviousNumber(i);
                strOperation.add(begin);
                String end = getNextNumber(i);
                strOperation.add(end);
                str.replace(i - begin.length(), i + end.length() + 1, opName + operationsNumber);
                i -= begin.length() - (opName + operationsNumber).length() + 1;
                _formula = str.toString();
            }
        }
        return strOperation;
    }

    /**
     * Get name of operations by its sign
     *
     * @param operation sign of the operation
     * @return name of the operation
     */
    private String getOperationsName(char operation) {
        String name = "";
        if (operation == '+') {
            name = "s";
        }
        if (operation == '^') {
            name = "p";
        }
        if (operation == '*') {
            name = "m";
        }
        return name;
    }

    /**
     * @param i position of the operations sign in the string
     * @return the number that comes after the sign
     */
    private String getNextNumber(int i) {
        StringBuilder str = new StringBuilder();
        for (int j = i + 1; j < _formula.length(); j++) {
            str.append(_formula.charAt(j));
            if (j != _formula.length() - 1) {
                if (OPERATIONS.contains(_formula.charAt(j + 1))) {
                    break;
                }
            }
        }
        return str.toString();
    }

    /**
     * @param i position of the operations sign in the string
     * @return the number that comes before the sign
     */
    private String getPreviousNumber(int i) {
        StringBuilder str = new StringBuilder();
        for (int j = i - 1; j >= 0; j--) {
            str.append(_formula.charAt(j));
            if (j != 0) {
                if (OPERATIONS.contains(_formula.charAt(j - 1))) {
                    break;
                }
            }
        }
        str.reverse();
        return str.toString();
    }

    /**
     * calculate the value of the expression
     *
     * @param variables all available variables
     */
    @Override
    public double calculate(HashMap<String, Double> variables) throws StringIndexOutOfBoundsException, NullPointerException {
        _variables = variables;
        calcChildExpressions();
        calcPowerData();
        calcMultiData();
        return calcSum();
    }

    /**
     * Calculating all subexpression from the map of child expressions.
     */
    private void calcChildExpressions() {
        int sign = 1;
        for (var key : _childExpressions.keySet()) {
            StringBuilder str = new StringBuilder(key);
            if (key.charAt(0) == '-') {
                str.deleteCharAt(0);
                sign = -1;
            }
            _variables.put(str.toString(), sign * _childExpressions.get(key).calculate(_variables));
        }
    }

    /**
     * Calculating the values of the power operations and place them to the hash map
     */
    private void calcPowerData() {
        int n = 1;
        for (int i = 0; i < _sPower.size() - 1; i += 2) {
            _variables.put("p" + (n), Math.pow(toNumbers(_sPower.get(i)), toNumbers(_sPower.get(i + 1))));
            n++;
        }
    }

    /**
     * Calculating the values of the multiplication operations and place them to the hash map
     */
    private void calcMultiData() {
        int n = 1;
        for (int i = 0; i < _sMulti.size() - 1; i += 2) {
            _variables.put("m" + (n), toNumbers(_sMulti.get(i)) * toNumbers(_sMulti.get(i + 1)));
            n++;
        }
    }

    /**
     * Calculate result sum of the expression
     *
     * @return result sum, the value of the expression
     */
    private double calcSum() throws StringIndexOutOfBoundsException {
        double sum = 0;
        for (String s : _sSum) {
            sum += toNumbers(s);
        }
        return sum;
    }

    /**
     * Convert the variable stored in string representation to double number
     * according to its value storing in HashMap
     *
     * @param str the variable string representation
     * @return the variable value
     */
    private double toNumbers(String str) throws StringIndexOutOfBoundsException {
        StringBuilder strNumber = new StringBuilder(str);
        int sign = 1;
        double number;
        try {
            number = Double.parseDouble(str);
        } catch (NumberFormatException e1) {
            if (strNumber.charAt(0) == '-') {
                sign = -1;
                strNumber.deleteCharAt(0);
            }
            if (strNumber.charAt(0) == '1') {
                try {
                    strNumber.delete(0, 2);
                    number = 1 / Double.parseDouble(strNumber.toString());
                } catch (NumberFormatException e2) {
                    number = 1 / _variables.get(strNumber.toString());
                }
            } else {
                number = _variables.get(strNumber.toString());
            }
        }
        return number * sign;
    }

    @Override
    public IAction clone() {
        return null;
    }

    @Override
    public void setArgument(String variable) {
    }
}
