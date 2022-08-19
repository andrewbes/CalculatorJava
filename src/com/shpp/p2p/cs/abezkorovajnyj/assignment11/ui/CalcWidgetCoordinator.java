package com.shpp.p2p.cs.abezkorovajnyj.assignment11.ui;

public class CalcWidgetCoordinator {

    CalcWidgetViewModel viewModel = new CalcWidgetViewModel();
    CalcWidgetController viewController = new CalcWidgetController();

    public void start() {
        CalcWidget calcWidget  = new CalcWidget(viewModel, viewController);
        viewController.setDelegate(calcWidget);
    }
}
