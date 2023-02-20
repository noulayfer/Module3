package com.fedorenko.action;

import com.fedorenko.util.UserInput;

public class StudentsWithGradeGreaterThanAction implements Action {
    @Override
    public void execute() {
        final String advertisement = "Enter value of grade to compare subject performance.";
        final Float aFloat = UserInput.getFloat(advertisement);
        SERVICE.logStudentsWithAvgGreaterThan(aFloat);
    }
}
