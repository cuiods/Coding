package edu.nju.exam.dao.impl;

import edu.nju.exam.dao.DataHelper;
import edu.nju.exam.dao.StudentDao;
import edu.nju.exam.model.StudentEntity;

/**
 * impl of studentDao
 */
public class StudentDaoImpl implements StudentDao {

    private DataHelper dataHelper;

    public StudentEntity find(int studentId) {
        return null;
    }

    public StudentEntity find(String studentName) {
        return null;
    }

    public boolean verify(String studentName, String password) {
        return false;
    }
}
