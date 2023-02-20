package com.fedorenko.model;

import com.fedorenko.util.LocalDateConverter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Entity
@ToString
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "student_id")
    private String id;
    private String name;
    private int age;

    @Column(name = "date_of_admission")
    @Convert(converter = LocalDateConverter.class)
    private LocalDate dateOfAdmission;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "group_id")
    private StudentGroup studentGroup;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Grade> grades;

    protected Student() {
    }

    public Student(final String name, final int age, final LocalDate dateOfAdmission) {
        this.name = name;
        this.age = age;
        this.dateOfAdmission = dateOfAdmission;
        grades = new ArrayList<>();
    }

    public void addGrade(final Grade grade) {
        Optional.ofNullable(grade).ifPresentOrElse(grd -> {
            grd.setStudent(this);
            grades.add(grd);
        }, () -> {
            throw new IllegalArgumentException("Grade cannot be null.");
        });
    }

    public List<Grade> getGrades() {
        return Collections.unmodifiableList(grades);
    }

    public static class StudentBuilder {
        private String name;
        private int age;
        private LocalDate dateOfAdmission;
        private StudentGroup studentGroup;
        private List<Grade> grades;

        public StudentBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public StudentBuilder withAge(int age) {
            this.age = age;
            return this;
        }

        public StudentBuilder withDateOfAdmission(LocalDate dateOfAdmission) {
            this.dateOfAdmission = dateOfAdmission;
            return this;
        }

        public StudentBuilder withStudentGroup(StudentGroup studentGroup) {
            this.studentGroup = studentGroup;
            return this;
        }

        public StudentBuilder withGrades(List<Grade> grades) {
            this.grades = grades;
            return this;
        }

        public Student build() {
            Student student = new Student(name, age, dateOfAdmission);
            student.setStudentGroup(studentGroup);
            if (grades != null) {
                grades.forEach(student::addGrade);
            }
            return student;
        }
    }
}
