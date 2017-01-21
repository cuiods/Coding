package edu.nju.exam.entity;

import java.io.Serializable;

/**
 * student entity
 */
public class StudentEntity implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8639638702874164269L;
	private int studentId;
    private String studentName;
    private String password;

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
