package service;

import entity.Course;
import entity.Enrollment;
import entity.Student;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileService {
    private static final String STUDENT_FILE = "data/students.txt";
    private static final String COURSE_FILE = "data/courses.txt";
    private static final String ENROLLMENT_FILE = "data/enrollments.txt";

    public static void saveStudents(List<Student> students) {
        File file = new File(STUDENT_FILE);
        File parentDir = file.getParentFile(); // Pega a pasta "data"

        // Se a pasta não existe, cria ela
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(STUDENT_FILE))) {
            for (Student s : students) {
                writer.write(s.getId() + ";" + s.getName() + ";" + s.getWorkload());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar alunos: " + e.getMessage());
        }
    }

    public static List<Student> loadStudents() {
        List<Student> students = new ArrayList<>();
        File file = new File(STUDENT_FILE);

        if (!file.exists()) return students;

        try (BufferedReader reader = new BufferedReader(new FileReader(STUDENT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                int id = Integer.parseInt(data[0]);
                String name = data[1];
                double workload = data.length > 2 ? Double.parseDouble(data[2]) : 0.0;

                Student student = new Student(id, name);
                student.setWorkload(workload);
                students.add(student);
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar alunos: " + e.getMessage());
        }
        return students;
    }

    public static void saveCourses(List<Course> courses) {
        File file = new File(COURSE_FILE);
        File parentDir = file.getParentFile();

        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(COURSE_FILE))) {
            for (Course c : courses) {
                writer.write(c.getId() + ";" + c.getName() + ";" + c.getWorkload() + ";" + c.getPeriod());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar disciplinas: " + e.getMessage());
        }
    }

    public static List<Course> loadCourses() {
        List<Course> courses = new ArrayList<>();
        File file = new File(COURSE_FILE);

        if (!file.exists()) return courses;

        try (BufferedReader reader = new BufferedReader(new FileReader(COURSE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                int id = Integer.parseInt(data[0]);
                String name = data[1];
                double workload = Double.parseDouble(data[2]);
                String period = data[3];

                courses.add(new Course(id, name, workload, period));
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar disciplinas: " + e.getMessage());
        }
        return courses;
    }

    public static void saveEnrollments(List<Enrollment> enrollments) {
        File file = new File(ENROLLMENT_FILE);
        File parentDir = file.getParentFile();

        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ENROLLMENT_FILE))) {
            for (Enrollment e : enrollments) {
                writer.write(e.getIdEnrollment() + ";" +
                        e.getIdStudent() + ";" +
                        e.getIdCourse() + ";" +
                        e.getGrade1() + ";" +
                        e.getGrade2() + ";" +
                        e.getAverage() + ";" +
                        e.getStatus());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar matrículas: " + e.getMessage());
        }
    }

    public static List<Enrollment> loadEnrollments() {
        List<Enrollment> enrollments = new ArrayList<>();
        File file = new File(ENROLLMENT_FILE);

        if (!file.exists()) return enrollments;

        try (BufferedReader reader = new BufferedReader(new FileReader(ENROLLMENT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");

                // Parseando os dados
                int idEnroll = Integer.parseInt(data[0]);
                int idStudent = Integer.parseInt(data[1]);
                int idCourse = Integer.parseInt(data[2]);
                double g1 = Double.parseDouble(data[3]);
                double g2 = Double.parseDouble(data[4]);
                double avg = Double.parseDouble(data[5]);
                String status = data[6];

                Enrollment e = new Enrollment(idEnroll, idStudent, idCourse);
                e.setGrade1(g1);
                e.setGrade2(g2);
                e.setAverage(avg);
                e.setStatus(status);

                enrollments.add(e);
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar matrículas: " + e.getMessage());
        }
        return enrollments;
    }
}