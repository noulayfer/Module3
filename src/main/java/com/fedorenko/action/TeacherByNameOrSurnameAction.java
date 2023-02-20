package com.fedorenko.action;

import com.fedorenko.util.UserInput;

public class TeacherByNameOrSurnameAction implements Action {
    @Override
    public void execute() {
        final String advertisement = "Enter firstname or lastname of teacher, you want to find.";
        final String string = UserInput.getString(advertisement);
        SERVICE.logTeacherByNameOrSurname(string);
    }
}
