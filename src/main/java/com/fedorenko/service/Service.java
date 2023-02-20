package com.fedorenko.service;

import com.fedorenko.model.Student;
import com.fedorenko.model.StudentGroup;
import com.fedorenko.repository.Repository;
import org.flywaydb.core.api.callback.Warning;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

public class Service {
    private static Repository<StudentGroup> studentGroupRepository;
    private static Repository<Student> studentRepository;
    private static Service service;
    private static final Logger LOGGER = LoggerFactory.getLogger(Service.class);
    private static final Random RANDOM = new Random();
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss:SSSS");


    public static Service getInstance() {
        if (service == null) {
            service = new Service(new Repository<>(StudentGroup.class), new Repository<>(Student.class));
            LOGGER.info("New service instance was created. " + LocalDateTime.now().format(DATE_TIME_FORMATTER));
        }
        return service;
    }

    private Service(final Repository<StudentGroup> repository, final Repository<Student> stRepository) {
        studentGroupRepository = repository;
        studentRepository = stRepository;
    }

    public List<StudentGroup> getAllStudentGroups() {
        return studentGroupRepository.getAll();
    }

    public StudentGroup getStudentGroupById(final String id) {
        return studentGroupRepository.getById(id);
    }

    public void saveStudentGroup(final StudentGroup studentGroup) {
        studentGroupRepository.save(studentGroup);
        LOGGER.info("Group: " + studentGroup + " were saved. " + LocalDateTime.now().format(DATE_TIME_FORMATTER));
    }

    public void deleteStudentGroupById(final String id) {
        studentGroupRepository.delete(id);
        LOGGER.info("Group with " + id + " id was deleted. " + LocalDateTime.now().format(DATE_TIME_FORMATTER));
    }

    public void saveStudent(final Student student) {
        studentRepository.save(student);
        LOGGER.info("Student: " + student + " was saved. " + LocalDateTime.now().format(DATE_TIME_FORMATTER));
    }

    public void logByName(final String name) {
        studentGroupRepository.searchByName(name)
                .forEach(g -> LOGGER.info(g.toString() + " " + LocalDateTime.now().format(DATE_TIME_FORMATTER)));
    }

    public void logStudentsCountByGroup() {
        studentGroupRepository.getStudentsCountByGroup()
                .forEach(gc -> LOGGER.info(gc.toString() + " " + LocalDateTime.now().format(DATE_TIME_FORMATTER)));
    }

    public void logAvgGradesByGroup() {
        studentGroupRepository.getAvgGradesByGroup()
                .forEach(gg -> LOGGER.info(gg.toString() + " " + LocalDateTime.now().
                        format(DATE_TIME_FORMATTER)));
    }

    public void logTeacherByNameOrSurname(final String nameOrSurname) {
        studentGroupRepository.getTeacherByNameOrSurname(nameOrSurname)
                .forEach(t -> LOGGER.info(t.toString() + " " + LocalDateTime.now().
                        format(DATE_TIME_FORMATTER)));
    }

    public void logSubjectPerformance() {
        studentGroupRepository.getSubjectPerformance()
                .forEach(s -> LOGGER.info(s.toString() + " " + LocalDateTime.now().
                        format(DATE_TIME_FORMATTER)));
    }

    public void logStudentsWithAvgGreaterThan(final float avgGrade) {
        studentGroupRepository.getStudentsWithAvgGradeGreaterThan(avgGrade)
                .forEach(s -> LOGGER.info(s.toString() + " " + LocalDateTime.now().
                        format(DATE_TIME_FORMATTER)));
    }

    @SuppressWarnings("all")
    public Student createStudent() {
        final String[] names = {"Sasha", "Oleg", "Maria", "Anna", "Ivan", "Katya", "Alex"};
        final Student.StudentBuilder studentBuilder = new Student.StudentBuilder();

        return studentBuilder
                .withAge(RANDOM.ints(1, 18, 22).findAny().getAsInt())
                .withName(names[RANDOM.nextInt(7)])
                .withDateOfAdmission(LocalDate.now())
                .withStudentGroup(new StudentGroup("Group " + RANDOM.nextInt(20)))
                .build();
    }
}
