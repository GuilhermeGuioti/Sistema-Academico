package entity;

import service.CourseService;

public class Course {
    private int id;
    private String name;
    private double workload;
    private String period;

    public Course(String name, double workload, String period) {
        this.id = -1;
        this.name = name;
        this.workload = workload;
        this.period = period;
    }

    public Course(int id, String name, double workload, String period) {
        this(name, workload, period);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWorkload() {
        return workload;
    }

    public void setWorkload(double workload) {
        this.workload = workload;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", workload=" + workload +
                ", period='" + period + '\'' +
                '}';
    }
}
