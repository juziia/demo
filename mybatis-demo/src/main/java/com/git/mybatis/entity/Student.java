package com.git.mybatis.entity;

public class Student {

    private long id;
    private String studentName;
    private int age;
    private String sex;
    private int clazz;

    public Student(long id, String studentName, int age, String sex, int clazz) {
        this.id = id;
        this.studentName = studentName;
        this.age = age;
        this.sex = sex;
        this.clazz = clazz;
    }

    private Clazz clazzObj;

    public Clazz getClazzObj() {
        return clazzObj;
    }

    public void setClazzObj(Clazz clazzObj) {
        this.clazzObj = clazzObj;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getClazz() {
        return clazz;
    }

    public void setClazz(int clazz) {
        this.clazz = clazz;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", studentName='" + studentName + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", clazz=" + clazz +
                ", clazzObj=" + clazzObj +
                '}';
    }
}
