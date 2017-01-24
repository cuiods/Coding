package edu.nju.exam.service.impl;

import edu.nju.exam.dao.StudentDao;
import edu.nju.exam.entity.StudentEntity;
import edu.nju.exam.service.StudentService;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class StudentServiceImpl implements StudentService {
	
	@EJB
	private StudentDao studentDao;

	@Override
	public StudentEntity find(int studentId) {
		return studentDao.find(studentId);
	}

	@Override
	public StudentEntity find(String studentName) {
		return studentDao.find(studentName);
	}

	@Override
	public boolean verify(String studentName, String password) {
		return studentDao.verify(studentName, password);
	}

}
