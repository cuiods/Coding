package edu.nju.exam.service.impl;

import edu.nju.exam.dao.StudentDao;
import edu.nju.exam.entity.StudentEntity;
import edu.nju.exam.service.StudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Student service impl
 * @author cuihao
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Resource
    private StudentDao studentDao;

    /**
     * find student by id
     *
     * @param studentId id of student
     * @return {@link StudentEntity}
     */
    @Override
    public StudentEntity find(int studentId) {
        return studentDao.find(studentId);
    }

    /**
     * find student by name
     *
     * @param studentName name of student
     * @return {@link StudentEntity}
     */
    @Override
    public StudentEntity find(String studentName) {
        return studentDao.find(studentName);
    }

    /**
     * find whether the {student,password} pair is exist.
     *
     * @param studentName name of student
     * @param password    password
     * @return true if exist, otherwise false
     */
    @Override
    public boolean verify(String studentName, String password) {
        return studentDao.verify(studentName,password);
    }
}
