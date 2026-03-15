package service;

import entity.Enrollment;
import entity.Student;
import entity.Course;
import exception.Exception;

import java.util.ArrayList;
import java.util.List;

public class EnrollmentService {
    private static final List<Enrollment> enrollments = new ArrayList<>();

    public static int generateIdEnrollment() {
        if(enrollments.isEmpty()){
            return 1;
        }
        Enrollment lastEnrollment = enrollments.get(enrollments.size() - 1);

        return lastEnrollment.getIdEnrollment() + 1;
    }

    public static Enrollment save(Enrollment newEnrollment){
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getIdStudent() == newEnrollment.getIdStudent()
                    && enrollment.getIdCourse() == newEnrollment.getIdCourse()) {
                throw new Exception("Este aluno já está matriculado nesta disciplina!");
            }
        }

        newEnrollment.setIdEnrollment(generateIdEnrollment());
        enrollments.add(newEnrollment);

        return newEnrollment;
    }

    public static Enrollment findEnrollment(int idStudent, int idCourse) {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getIdStudent() == idStudent && enrollment.getIdCourse() == idCourse) {
                return enrollment;
            }
        }

        throw new Exception("Matrícula não encontrada para este aluno nesta disciplina.");
    }

    public static List<Enrollment> findByStudent(int idStudent) {
        List<Enrollment> result = new ArrayList<>();

        for (Enrollment enrollment : enrollments) {
            if (enrollment.getIdStudent() == idStudent) {
                result.add(enrollment);
            }
        }

        if (result.isEmpty()) {
            throw new Exception("Este aluno não possui matrículas.");
        }

        return result;
    }

    public static List<Enrollment> findByCourse(int idCourse) {
        List<Enrollment> result = new ArrayList<>();

        for (Enrollment enrollment : enrollments) {
            if (enrollment.getIdCourse() == idCourse) {
                result.add(enrollment);
            }
        }

        if (result.isEmpty()) {
            throw new Exception("Não há alunos matriculados nesta disciplina.");
        }

        return result;
    }

    public static void delete(int idStudent, int idCourse){
        Enrollment enrollment = findEnrollment(idStudent, idCourse);

        enrollments.remove(enrollment);
    }
}
