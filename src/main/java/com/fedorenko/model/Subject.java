package com.fedorenko.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Optional;

@Getter
@Setter
@Entity
@ToString
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "subject_id")
    private String id;
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "grade_id")
    private Grade grade;

    protected Subject() {
    }

    public Subject(final String name) {
        this.name = name;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
        Optional.ofNullable(teacher)
                .ifPresentOrElse(teach -> teach.setSubject(this),
                        () -> {
                            throw new IllegalArgumentException("Teacher cannot be null.");
                        });
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
        Optional.ofNullable(grade)
                .ifPresentOrElse(gr -> gr.setSubject(this),
                        () -> {
                            throw new IllegalArgumentException("Grade cannot be null.");
                        });
    }

    public Teacher getTeacher() {
        return teacher != null ? new Teacher(teacher.getFirstname(), teacher.getLastname(), teacher.getAge(),
                teacher.getSubject()) : null;
    }

    public Grade getGrade() {
        return grade != null ? new Grade(grade.getSubject(), grade.getValue()) : null;
    }
}
