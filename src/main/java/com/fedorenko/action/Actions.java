package com.fedorenko.action;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Actions {
    AVERAGE_GRADE("Show average grade by each group.", new AvgGradeByGroupAction()),
    GROUP_BY_NAME("Search for a group by it`s name.", new GroupByNameAction()),
    AMOUNT_OF_STUDENTS_BY_GROUP("Show amount of students in each group.", new AmountOfStudentsByGroupAction()),
    STUDENTS_WITH_GRADE_GREATER("Show all students that have grade greater than entered value.",
            new StudentsWithGradeGreaterThanAction()),
    SUBJECT_PERFORMANCE("Show the statistics of subject performance.", new SubjectPerformanceAction()),
    TEACHER_BY_NAME_OR_SURNAME("Search for a teacher by name or surname.", new TeacherByNameOrSurnameAction()),
    CREATE_RANDOM_STUDENT("Create random student.", new CreateRandomStudentAction()),
    EXIT("End the program.", new ExitAction())
    ;
    private final String name;
    private final Action action;
    Actions(String name, Action action) {
        this.name = name;
        this.action = action;
    }
    public void execute() {
        action.execute();
    }
    public static String[] mapToNames() {
        return Arrays.stream(Actions.values())
                .map(Actions::getName)
                .toArray(String[]::new);
    }
}

