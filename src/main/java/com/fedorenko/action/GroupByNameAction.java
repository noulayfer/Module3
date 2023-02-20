package com.fedorenko.action;

import com.fedorenko.util.UserInput;

public class GroupByNameAction implements Action {
    @Override
    public void execute() {
        final String advertisement = "Write name of group and I will find it!";
        final String userInput = UserInput.getString(advertisement);
        SERVICE.logByName(userInput);
    }
}
