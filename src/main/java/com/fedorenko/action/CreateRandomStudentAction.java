package com.fedorenko.action;

import com.fedorenko.model.Student;

public class CreateRandomStudentAction implements Action{
    @Override
    public void execute() {
        final Student student = SERVICE.createStudent();
        SERVICE.saveStudent(student);
    }
}
