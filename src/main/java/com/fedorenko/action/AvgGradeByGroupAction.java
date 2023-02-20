package com.fedorenko.action;

public class AvgGradeByGroupAction implements Action {
    @Override
    public void execute() {
        SERVICE.logAvgGradesByGroup();
    }
}
