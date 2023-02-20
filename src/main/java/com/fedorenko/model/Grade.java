package com.fedorenko.model;

import com.fedorenko.util.FloatConverter;
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
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "grade_id")
    private String id;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    @ToString.Exclude
    private Subject subject;
    @Convert(converter = FloatConverter.class)
    private float value;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    private Student student;

    protected Grade() {
    }

    public Grade(final Subject subject, final float value) {
        this.value = value;
        this.setSubject(subject);
    }

    public void setSubject(final Subject subject) {
        this.subject = subject;
        Optional.ofNullable(subject).ifPresentOrElse(subj -> subj.setGrade(this), () -> {
            throw new IllegalArgumentException("Subject cannot be null.");
        });
    }

    public Subject getSubject() {
        return new Subject(subject.getName());
    }
}
