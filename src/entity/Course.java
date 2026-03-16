package entity;

public class Course {
    private int id;
    private String name;
    private double workload;
    private String period;

    public Course() {}

    public Course(int id, String name, double workload, String period) {
        this.id = id;
        this.name = name;
        this.workload = workload;
        this.period = period;
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
