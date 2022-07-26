package edu.hitwh.mybatis.pojo;

public class Student {
    private Integer id;
    private String studentName;
    private Integer age;
    private String email;
    private Major major;

    public Student() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", studentName='" + studentName + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", major=" + major +
                '}';
    }

    public Student(String studentName, Integer age, String email) {
        this.studentName = studentName;
        this.age = age;
        this.email = email;
    }
}
