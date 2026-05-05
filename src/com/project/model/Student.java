package com.project.model;

import java.io.Serializable;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String course;
    private String rollNo;

    public Student(String name, String course, String rollNo) {
        this.name = name;
        this.course = course;
        this.rollNo = rollNo;
    }

    public String getName() { return name; }
    public String getCourse() { return course; }
    public String getRollNo() { return rollNo; }
}