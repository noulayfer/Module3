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
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "teacher_id")
    private String id;

    @Column(name = "first_name")

    private String firstname;
    @Column(name = "last_name")
    private String lastname;
    private int age;
    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    @ToString.Exclude
    private Subject subject;

    protected Teacher() {
    }

    public Teacher(final String firstname, final String lastname, final int age, final Subject subject) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.setSubject(subject);
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
        Optional.ofNullable(subject)
                .ifPresentOrElse(subj -> subj.setTeacher(this),
                        () -> {
                            throw new IllegalArgumentException("Subject cannot be null.");
                        });
    }

    public Subject getSubject() {
        return subject != null ? new Subject(subject.getName()) : null;
    }
}
