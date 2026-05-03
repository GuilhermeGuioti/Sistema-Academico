package service;

import entity.Student;
import exception.Exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class StudentService {
    private static final List<Student> students = new ArrayList<>();

    public static void loadAll() {
        List<Student> loaded = FileService.loadStudents();
        students.clear();
        students.addAll(loaded);
    }

    public static int genereteRA() {
        Random rand = new Random();
        int RA = -1;

        do {
            int newRA = rand.nextInt(100000, 999999);

            Optional<Student> optionalStudent = students.stream()
                    .filter(student -> newRA == student.getId())
                    .findFirst();

            if (optionalStudent.isEmpty()) RA = newRA;
        } while (RA == -1);

        return RA;
    }

    public static Student save(Student newStudent){
        if (newStudent.getName() == null || newStudent.getName().trim().isEmpty()) {
            throw new Exception("O nome do aluno não pode ser vazio.");
        }

        int id = genereteRA();
        newStudent.setId(id);

        students.add(newStudent);

        FileService.saveStudents(students);

        return newStudent;
    }

    public static List<Student> findAll(){
        if(students.isEmpty()){
            throw new Exception("Não há alunos");
        }

        return students;
    }


    public static Student findById(int id){
        for(Student student : students){
            if(student.getId() == id){
                return student;
            }
        }

        throw new Exception("Aluno com ID " + id + " não encontrado.");
    }

    public static void delete(int id) {
        Student student = findById(id);

        students.remove(student);

        FileService.saveStudents(students);
    }

    public static Student update(Student updatedData){
        Student student = findById(updatedData.getId());

        if (updatedData.getName() != null && !updatedData.getName().trim().isEmpty()) {
            student.setName(updatedData.getName());
        }

        FileService.saveStudents(students);

        return student;
    }

    public static void updateWorkload(int idStudent, double delta) {
        Student student = findById(idStudent);
        student.setWorkload(student.getWorkload() + delta);
        FileService.saveStudents(students);
    }

    public static void resetAllWorkloads() {
        for (Student student : students) {
            student.setWorkload(0);
        }
        FileService.saveStudents(students);
    }
}