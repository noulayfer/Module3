package com.fedorenko.action;

public class AmountOfStudentsByGroupAction implements Action{
    @Override
    public void execute() {
        SERVICE.logStudentsCountByGroup();
    }
}
