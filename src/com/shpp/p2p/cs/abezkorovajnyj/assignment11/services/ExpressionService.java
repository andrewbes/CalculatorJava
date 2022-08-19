package com.shpp.p2p.cs.abezkorovajnyj.assignment11.services;
import com.shpp.p2p.cs.abezkorovajnyj.assignment11.model.Expression;

import java.util.Arrays;
import java.util.HashMap;

public class ExpressionService {
   public static final ExpressionService shared = new ExpressionService();
   private Expression expression;
   private HashMap<String, Double> variables;
   private String[] variablesNames;
   private Double result;

   public void initWith(String[] args) {
       try {
           expression = new Expression(args[0].replaceAll("\\s", ""));
           setVariables(args);

       } catch (Exception e) {
           System.out.println("Wrong arguments " + e);
       }
   }

    /**
     * Transform string array of arguments into HashMap
     *
     * @param args Arguments in string format array
     */
    public void setVariables(String[] args) {
        variables = getVariablesFromExpression(args);

        for (int i = 1; i < args.length; i++) {
            String[] arg = args[i].replaceAll("\\s", "").split("=");
            variables.put(arg[0], Double.parseDouble(arg[1]));
        }
        variablesNames = variables.keySet().toArray(new String[0]);
        Arrays.sort(variablesNames);
        result = null;
    }

    private HashMap<String, Double> getVariablesFromExpression(String[] args) {
       var variables = new HashMap<String, Double>();
        for (int i=0; i< args[0].length(); i++){
            if(isVariable(args[0],i)){
                variables.put(""+args[0].charAt(i), 0.0);
            }
        }
        return variables;
    }

    private boolean isVariable(String arg, int i) {
        if (arg.length()<2) {return  false;}
        boolean nextIsLetter;
        boolean prevIsLetter;
        if (i==0){
            nextIsLetter = Character.isLetter(arg.charAt(i + 1));
            return Character.isLetter(arg.charAt(i))&& !nextIsLetter;
        }
        if (i==arg.length()-1){
            prevIsLetter = Character.isLetter(arg.charAt(i - 1));
            return Character.isLetter(arg.charAt(i))&&!prevIsLetter;
        }
        prevIsLetter = Character.isLetter(arg.charAt(i - 1));
        nextIsLetter = Character.isLetter(arg.charAt(i + 1));
        return Character.isLetter(arg.charAt(i))&&
                !prevIsLetter&&
                !nextIsLetter;
    }

    public Double getResult() {
        return result;
    }

    public void calculate()  {
        try {
            result = expression.calculate(variables);
        } catch ( Exception e) {
            result = null;
        }

    }

    public String getExpressionStr(){
        return expression.getExpressionStr();
    }
    public String[] getVariablesNames() { return  variablesNames;}
    public double getVariableValue(int index){
        return variables.get(variablesNames[index]);
    }
    public int getVariablesCount() { return variablesNames.length;}
    public String getVariableName(int index) { return variablesNames[index];}

    public void setVariable(String key, String text) {
        variables.put(key,Double.parseDouble(text));
    }
}
