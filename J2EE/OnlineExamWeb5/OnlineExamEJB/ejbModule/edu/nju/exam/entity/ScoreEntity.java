package edu.nju.exam.entity;

import java.io.Serializable;

/**
 * score entity
 */
public class ScoreEntity implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7161938029310128487L;
	private int studentId;
    private int courseId;
    private String courseName;
    private int score;
    private String type;

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
