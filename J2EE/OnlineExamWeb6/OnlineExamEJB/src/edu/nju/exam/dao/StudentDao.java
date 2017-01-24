package edu.nju.exam.dao;

import edu.nju.exam.entity.StudentEntity;

import javax.ejb.Remote;

/**
 * student dao interface
 */
@Remote
public interface StudentDao {
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
