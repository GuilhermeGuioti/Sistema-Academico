package entity;

public class Enrollment {
    private int idEnrollment;
    private int idStudent;
    private int idCourse;
    private double grade1;
    private double grade2;
    private double average;
    private String status;

    public Enrollment(int idStudent, int idCourse) {
        this.idEnrollment = -1;
        this.idStudent = idStudent;
        this.idCourse = idCourse;
        this.grade1 = 0.0;
        this.grade2 = 0.0;
        this.average = 0.0;
        this.status = "Sem nota";
    }

    public Enrollment(int idEnrollment, int idStudent, int idCourse) {
        this(idStudent, idCourse);
        this.idEnrollment = idEnrollment;
    }

    public int getIdEnrollment() {
        return idEnrollment;
    }

    public void setIdEnrollment(int idEnrollment) {
        this.idEnrollment = idEnrollment;
    }

    public int getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }

    public int getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(int idCourse) {
        this.idCourse = idCourse;
    }

    public double getGrade1() {
        return grade1;
    }

    public void setGrade1(double grade1) {
        this.grade1 = grade1;
    }

    public double getGrade2() {
        return grade2;
    }

    public void setGrade2(double grade2) {
        this.grade2 = grade2;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "idEnrollment=" + idEnrollment +
                ", idStudent=" + idStudent +
                ", idCourse=" + idCourse +
                ", grade1=" + grade1 +
                ", grade2=" + grade2 +
                ", average=" + average +
                '}';
    }
}
