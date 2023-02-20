package com.fedorenko.action;

public class ExitAction implements Action {
    @Override
    public void execute() {
        System.exit(0);
    }
}
