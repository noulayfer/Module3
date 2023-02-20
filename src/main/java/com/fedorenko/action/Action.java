package com.fedorenko.action;

import com.fedorenko.service.Service;

public interface Action {
    Service SERVICE = Service.getInstance();

    void execute();
}
