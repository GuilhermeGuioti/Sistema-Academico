package service;

import entity.Course;
import exception.Exception;

import java.util.ArrayList;
import java.util.List;

public class CourseService {
    private static final List<Course> courses = new ArrayList<>();

    public static void loadAll() {
        List<Course> loaded = FileService.loadCourses();
        courses.clear();
        courses.addAll(loaded);
    }

    public static int generateIdCourse() {
        if(courses.isEmpty()){
            return 1;
        }

        Course lastCourse = courses.get(courses.size() - 1);

        return lastCourse.getId() + 1;
    }

    public static Course save(Course newCourse){
        if (newCourse == null) {
            throw new Exception("Não é possível salvar uma disciplina nula.");
        }

        if (newCourse.getName() == null || newCourse.getName().trim().isEmpty()) {
            throw new Exception("O nome da disciplina é obrigatório.");
        }

        if (newCourse.getWorkload() <= 0) {
            throw new Exception("A carga horária deve ser um valor positivo.");
        }

        if (newCourse.getPeriod() == null || newCourse.getPeriod().trim().isEmpty()) {
            throw new Exception("O periodo da disciplina é obrigatório.");
        }

        int id = generateIdCourse();
        newCourse.setId(id);

        courses.add(newCourse);

        FileService.saveCourses(courses);

        return newCourse;
    }

    public static List<Course> findAll() {
        if (courses.isEmpty()) {
            throw new Exception("Não há disciplinas");
        }

        return courses;
    }

    public static Course findById(int id){
        for(Course course : courses){
            if(course.getId() == id){
                return course;
            }
        }

        throw new Exception("Disciplina com ID " + id + " não encontrada.");
    }

    public static void delete(int id){
        Course course = findById(id);

        courses.remove(course);
    }

    public static Course update(Course updatedData) {
        Course course = findById(updatedData.getId());

        if (updatedData.getName() != null && !updatedData.getName().trim().isEmpty()) {
            course.setName(updatedData.getName());
        }

        if (updatedData.getWorkload() > 0) {
            course.setWorkload(updatedData.getWorkload());
        }

        if (updatedData.getPeriod() != null && !updatedData.getPeriod().trim().isEmpty()) {
            course.setPeriod(updatedData.getPeriod());
        }

        return course;
    }
}