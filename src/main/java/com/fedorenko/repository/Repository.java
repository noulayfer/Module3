package com.fedorenko.repository;

import com.fedorenko.model.Student;
import com.fedorenko.model.StudentGroup;
import com.fedorenko.model.Teacher;
import com.fedorenko.util.HibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.stream.Collectors;

public class Repository<T> {
    Class<T> tClass;

    public Repository(Class<T> tClass) {
        this.tClass = tClass;
    }

    public void save(T t) {
        final EntityManager entityManager = HibernateUtil.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(t);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<T> getAll() {
        final EntityManager entityManager = HibernateUtil.getEntityManager();
        return entityManager.createQuery("from " + tClass.getSimpleName(), tClass)
                .getResultList();
    }

    public T getById(final String id) {
        final EntityManager entityManager = HibernateUtil.getEntityManager();
        return Optional.ofNullable(entityManager.find(tClass, id))
                .orElseThrow(IllegalArgumentException::new);
    }

    public void delete(final String id) {
        final EntityManager entityManager = HibernateUtil.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("delete from " + tClass.getSimpleName() + " where id = :id")
                .setParameter("id", id)
                .executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public List<StudentGroup> searchByName(final String name) {
        final EntityManager entityManager = HibernateUtil.getEntityManager();
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<StudentGroup> query = criteriaBuilder.createQuery(StudentGroup.class);
        final Root<StudentGroup> root = query.from(StudentGroup.class);
        query.where(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
        final TypedQuery<StudentGroup> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }

    public List<GroupCountDTO> getStudentsCountByGroup() {
        final EntityManager entityManager = HibernateUtil.getEntityManager();
        List<Object[]> groupCounts = entityManager.createQuery(
                "select g.name, count(s) from Student s join s.studentGroup g group by g.name",
                Object[].class).getResultList();
        entityManager.close();

        return groupCounts.stream()
                .map(gc -> new GroupCountDTO(gc[0].toString(), ((Number) gc[1]).intValue()))
                .collect(Collectors.toList());
    }

    @SuppressWarnings("all")
    public List<GroupGradeDTO> getAvgGradesByGroup() {
        final EntityManager entityManager = HibernateUtil.getEntityManager();
        final List<Object[]> resultList = entityManager.createQuery(
                        "SELECT g.student.studentGroup.name, AVG(g.value) FROM Grade g GROUP BY g.student.studentGroup.name")
                .getResultList();
        entityManager.close();

        return resultList.stream()
                .map(gg -> new GroupGradeDTO(gg[0].toString(), Float.parseFloat(gg[1].toString())))
                .collect(Collectors.toList());
    }

    public List<Teacher> getTeacherByNameOrSurname(final String nameOrSurname) {
        final EntityManager entityManager = HibernateUtil.getEntityManager();
        return entityManager.createQuery(
                        "SELECT t FROM Teacher t WHERE lower(t.firstname) LIKE lower(:nameOrSurname) OR lower(t.lastname) LIKE lower(:nameOrSurname)",
                        Teacher.class)
                .setParameter("nameOrSurname", "%" + nameOrSurname + "%")
                .getResultList();
    }

    public List<SubjectGradeDTO> getSubjectPerformance() {
        final EntityManager entityManager = HibernateUtil.getEntityManager();
        final List<SubjectGradeDTO> resultList = new ArrayList<>();
        resultList.addAll(getSubjectWithMaxPerformance(entityManager));
        resultList.addAll(getSubjectWithMinPerformance(entityManager));
        entityManager.close();

        return resultList;
    }

    public List<Student> getStudentsWithAvgGradeGreaterThan(final double avgGrade) {
        final EntityManager entityManager = HibernateUtil.getEntityManager();
        return entityManager.createQuery("SELECT s FROM Student s JOIN s.grades g GROUP BY s.id HAVING AVG(g.value) > :avgGrade",
                        Student.class)
                .setParameter("avgGrade", avgGrade)
                .getResultList();
    }

    @SuppressWarnings("all")
    private List<SubjectGradeDTO> getSubjectWithMaxPerformance(final EntityManager entityManager) {
        final List<Object[]> maxResult = entityManager.createNativeQuery(
                        "SELECT s.name, AVG(g.value) FROM grade g " +
                                "JOIN subject s ON g.subject_id = s.subject_id " +
                                "GROUP BY s.name ORDER BY AVG(g.value) DESC LIMIT 1")
                .getResultList();
        return objectsToSubject(maxResult);
    }

    @SuppressWarnings("all")
    private List<SubjectGradeDTO> getSubjectWithMinPerformance(final EntityManager entityManager) {
        final List<Object[]> minResult = entityManager.createNativeQuery(
                        "SELECT s.name, AVG(g.value) FROM grade g " +
                                "JOIN subject s ON g.subject_id = s.subject_id " +
                                "GROUP BY s.name ORDER BY AVG(g.value) ASC LIMIT 1")
                .getResultList();
        return objectsToSubject(minResult);
    }

    private List<SubjectGradeDTO> objectsToSubject(final List<Object[]> list) {
        return list.stream()
                .map(sg -> new SubjectGradeDTO(sg[0].toString(), Float.parseFloat(sg[1].toString())))
                .collect(Collectors.toList());
    }
}
