package edu.nju.exam.service;

import javax.ejb.Remote;

import edu.nju.exam.entity.StudentEntity;

@Remote
public interface StudentService {
	
	/**
     * find student by id
     * @param studentId id of student
     * @return {@link StudentEntity}
     */
    StudentEntity find(int studentId);

    /**
     * find student by name
     * @param studentName name of student
     * @return {@link StudentEntity}
     */
    StudentEntity find(String studentName);

    /**
     * find whether the {student,password} pair is exist.
     * @param studentName name of student
     * @param password password
     * @return true if exist, otherwise false
     */
    boolean verify(String studentName, String password);
}
