package service;

import entity.Course;
import entity.Enrollment;
import entity.Student;
import exception.Exception;

import java.util.ArrayList;
import java.util.List;

public class EnrollmentService {
    private static final List<Enrollment> enrollments = new ArrayList<>();

    public static void loadAll() {
        List<Enrollment> loaded = FileService.loadEnrollments();
        enrollments.clear();
        enrollments.addAll(loaded);
    }

    public static int generateIdEnrollment() {
        int maxId = 0;
        for (Enrollment e : enrollments) {
            if (e.getIdEnrollment() > maxId) maxId = e.getIdEnrollment();
        }
        return maxId + 1;
    }

    public static Enrollment save(Enrollment newEnrollment){
        Student student = StudentService.findById(newEnrollment.getIdStudent());
        Course course = CourseService.findById(newEnrollment.getIdCourse());

        for (Enrollment enrollment : enrollments) {
            if (enrollment.getIdStudent() == newEnrollment.getIdStudent()
                    && enrollment.getIdCourse() == newEnrollment.getIdCourse()) {
                throw new Exception("Este aluno já está matriculado nesta disciplina!");
            }
        }

        if (student.getWorkload() + course.getWorkload() > 300) {
            throw new Exception("Carga horária excede o limite de 300h no semestre. " +
                    "Atual: " + student.getWorkload() + "h | Nova disciplina: " + course.getWorkload() + "h");
        }

        newEnrollment.setIdEnrollment(generateIdEnrollment());
        enrollments.add(newEnrollment);
        FileService.saveEnrollments(enrollments);
        StudentService.updateWorkload(newEnrollment.getIdStudent(), course.getWorkload());

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

        return result;
    }

    public static List<Enrollment> findByCourse(int idCourse) {
        List<Enrollment> result = new ArrayList<>();

        for (Enrollment enrollment : enrollments) {
            if (enrollment.getIdCourse() == idCourse) {
                result.add(enrollment);
            }
        }

        return result;
    }

    public static void delete(int idStudent, int idCourse){
        Enrollment enrollment = findEnrollment(idStudent, idCourse);
        Course course = CourseService.findById(idCourse);

        enrollments.remove(enrollment);
        FileService.saveEnrollments(enrollments);
        double removedWorkload = -course.getWorkload();
        StudentService.updateWorkload(idStudent, removedWorkload);
    }

    public static void deleteByStudent(int idStudent) {
        enrollments.removeIf(e -> e.getIdStudent() == idStudent);
        FileService.saveEnrollments(enrollments);
    }

    public static void deleteByCourse(int idCourse) {
        Course course = CourseService.findById(idCourse);
        double courseWorkload = course.getWorkload();

        List<Enrollment> toRemove = new ArrayList<>();
        for (Enrollment e : enrollments) {
            if (e.getIdCourse() == idCourse) {
                toRemove.add(e);
            }
        }

        for (Enrollment e : toRemove) {
            enrollments.remove(e);
            StudentService.updateWorkload(e.getIdStudent(), -courseWorkload);
        }

        FileService.saveEnrollments(enrollments);
    }

    public static void updateGrade(int idStudent, int idCourse, int examNumber, double value){
        Enrollment enrollment = findEnrollment(idStudent, idCourse);

        if (value > 10 || value < 0) {
            throw new Exception("A nota deve estar entre 0 e 10.");
        }

        if(examNumber == 1){
            enrollment.setGrade1(value);
        } else if(examNumber == 2){
            enrollment.setGrade2(value);
        } else{
            throw new Exception("Número de prova inválido. Use 1 para P1 ou 2 para P2.");
        }

        double newAverage = (enrollment.getGrade1() + enrollment.getGrade2()) / 2;
        enrollment.setAverage(newAverage);

        if(newAverage >= 6){
            enrollment.setStatus("Aprovado");
        } else{
            enrollment.setStatus("Reprovado");
        }

        FileService.saveEnrollments(enrollments);
    }
}
