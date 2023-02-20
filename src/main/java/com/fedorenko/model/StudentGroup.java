package com.fedorenko.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Entity
@Table(name = "student_group")
@ToString
public class StudentGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "group_id")
    private String id;
    private String name;

    @OneToMany(mappedBy = "studentGroup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Student> students;

    protected StudentGroup() {
    }

    public StudentGroup(final String name) {
        this.name = name;
        students = new ArrayList<>();
    }

    public void addStudent(final Student student) {
        Optional.ofNullable(student).ifPresent(s -> {
            s.setStudentGroup(this);
            students.add(s);
        });
    }

    public List<Student> getStudents() {
        return Collections.unmodifiableList(students);
    }

}
