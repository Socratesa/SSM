package edu.hitwh.mybatis.pojo;

import java.util.List;

public class Major {
    private Integer id;
    private String majorName;
    private String majorPrincipal;
    private String description;
    private List<Student> students;

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Major() {
    }

    @Override
    public String toString() {
        return "Major{" +
                "id=" + id +
                ", majorName='" + majorName + '\'' +
                ", majorPrincipal='" + majorPrincipal + '\'' +
                ", description='" + description + '\'' +
                ", students=" + students +
                '}';
    }

    public Major(String majorName, String majorPrincipal, String description) {
        this.majorName = majorName;
        this.majorPrincipal = majorPrincipal;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getMajorPrincipal() {
        return majorPrincipal;
    }

    public void setMajorPrincipal(String majorPrincipal) {
        this.majorPrincipal = majorPrincipal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
