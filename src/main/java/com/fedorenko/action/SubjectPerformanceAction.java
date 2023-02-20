package com.fedorenko.action;

public class SubjectPerformanceAction implements Action {
    @Override
    public void execute() {
        SERVICE.logSubjectPerformance();
    }
}
