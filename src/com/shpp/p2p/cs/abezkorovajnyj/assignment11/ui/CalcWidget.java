package com.shpp.p2p.cs.abezkorovajnyj.assignment11.ui;

import com.shpp.p2p.cs.abezkorovajnyj.assignment11.ui.widgets.CalcJButton;
import com.shpp.p2p.cs.abezkorovajnyj.assignment11.ui.widgets.CalcJTextField;
import com.shpp.p2p.cs.abezkorovajnyj.assignment11.ui.widgets.Spacer;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.HashMap;


public class CalcWidget extends JFrame implements CalcWidgetControllerDelegate {

    private static final int EXPR_LABEL_HEIGHT = 30;
    private static final int VAR_LABEL_HEIGHT = 50;
    private static final int LABEL_VERTICAL_PADDING = 12;
    private static final int PANEL_WIDTH = 600;

    Container mainContainer;
    Border br = BorderFactory.createLineBorder(new Color(0X63CA55));
    JPanel expressionPanel;
    JPanel variablesPanel;
    JPanel resultPanel;

    private JTextField tfExpression;
    private JTextField tfResult;
    private HashMap<String,JTextField> tfVariables = new HashMap<>();

    private final CalcWidgetViewModel model;
    private final CalcWidgetController controller;

    private int resultPanelHeight;
    private int variablesPanelHeight;
    private int expressionPanelHeight;

    public CalcWidget(CalcWidgetViewModel viewModel, CalcWidgetController controller)  {
        this.model = viewModel;
        this.controller = controller;
        setupUI();
    }

    private void setupUI() {
        setTitle("Expressions Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        mainContainer=getContentPane();
        createExpressionPanel();
        createResultPanel();
        mainContainer.setBackground(Color.white);
        initMenu();
        setResizable(false);
        setVisible(true);

        updateUI();
    }

    private void createResultPanel() {
        resultPanelHeight = EXPR_LABEL_HEIGHT+LABEL_VERTICAL_PADDING;
        resultPanel = new JPanel();
        resultPanel.setLayout(new FlowLayout());
        resultPanel.setBackground(Color.white);

        tfResult = new CalcJTextField("", 20, 0x2F7C25, 0X63CA55);
        tfResult.setPreferredSize(new Dimension(450,EXPR_LABEL_HEIGHT));
        resultPanel.add(tfResult);

        JButton btnCalc = new CalcJButton("CALCULATE", 12, 0x2F7C25, br);
        resultPanel.add(btnCalc);
        btnCalc.addActionListener(e -> actBtnCalcPressed());
        resultPanel.setPreferredSize(new Dimension(PANEL_WIDTH,resultPanelHeight));
        mainContainer.add(resultPanel, BorderLayout.PAGE_END);
    }

    private void createVariablesPanel() {
        if (variablesPanel != null) {
            getContentPane().remove(variablesPanel);
        }

        variablesPanel = new JPanel();
        variablesPanel.setLayout(new GridLayout(0,4,0,20));
        variablesPanel.setBackground(Color.white);
        var pairHeight = model.getVariablesCount()/2 * (VAR_LABEL_HEIGHT + LABEL_VERTICAL_PADDING);
        var notPairHeight = model.getVariablesCount()/2* (VAR_LABEL_HEIGHT  + LABEL_VERTICAL_PADDING) + VAR_LABEL_HEIGHT;
        var height = model.getVariablesCount()%2 == 0 ? pairHeight: notPairHeight;
        variablesPanelHeight = model.getVariablesNames().length * VAR_LABEL_HEIGHT/2;
        variablesPanel.setMaximumSize(new Dimension(PANEL_WIDTH,height));
        createVariables();
        getContentPane().add(new Spacer(50,0), BorderLayout.EAST);
        getContentPane().add(variablesPanel, BorderLayout.CENTER);

        var frameHeight = resultPanelHeight+variablesPanelHeight+expressionPanelHeight;
        Dimension dim = new Dimension(PANEL_WIDTH,frameHeight );
        setMinimumSize(dim);

        invalidate();
        validate();
        pack();
    }

    private void createVariables() {
        tfVariables.clear();
        for (int i=0; i< model.getVariablesCount(); i++ ){
            String name = model.getVariableName(i);
            var bt = new CalcJButton("  =", 40, 0X63CA55, "resources/"+name+".png");
            var value = model.getVariableValue(i);
            var tf = new CalcJTextField(value, 30, 0x2F7C25, 0X63CA55);
            bt.setPreferredSize(new Dimension(20,VAR_LABEL_HEIGHT));
            tf.setPreferredSize(new Dimension(100,VAR_LABEL_HEIGHT));
            tfVariables.put(name,tf);
            variablesPanel.add(bt);
            variablesPanel.add(tf);
        }
    }

    private void createExpressionPanel() {
        expressionPanelHeight = EXPR_LABEL_HEIGHT+LABEL_VERTICAL_PADDING;;
        expressionPanel = new JPanel();
        expressionPanel.setLayout(new FlowLayout());
        expressionPanel.setBackground(Color.white);

        tfExpression = new CalcJTextField(model.getExpressionStr(), 20, 0x2F7C25, 0X63CA55);
        tfExpression.setPreferredSize(new Dimension(450,EXPR_LABEL_HEIGHT));
        expressionPanel.add(tfExpression);

        JButton btnParse = new CalcJButton("PARS", 12, 0x2F7C25, br);
        expressionPanel.add(btnParse);

        btnParse.addActionListener(e -> actBtnParsePressed());

        expressionPanel.setPreferredSize(new Dimension(PANEL_WIDTH,expressionPanelHeight));
        mainContainer.add(expressionPanel, BorderLayout.PAGE_START);
    }

    private void initMenu() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu file = new JMenu("File");
        JMenuItem quit = new JMenuItem("Quit");
        quit.addActionListener(e -> actMenuExitPressed());
        file.add(quit);
        menuBar.add(file);
    }

    private void actBtnParsePressed() {
        controller.parse(tfExpression.getText());
    }
    private void actBtnCalcPressed(){ controller.calculate(tfVariables);}
    private void actMenuExitPressed() {
        System.exit(0);
    }
    public void updateUI() {
        tfExpression.setText(model.getExpressionStr());
        createVariablesPanel();
        var result = model.getResult();
        tfResult.setText(result);
    }
}
