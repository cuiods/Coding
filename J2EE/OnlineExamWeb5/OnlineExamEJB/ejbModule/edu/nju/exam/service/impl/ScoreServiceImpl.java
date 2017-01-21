package edu.nju.exam.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import edu.nju.exam.dao.ScoreDao;
import edu.nju.exam.entity.ScoreEntity;
import edu.nju.exam.service.ScoreService;

@Stateless
public class ScoreServiceImpl implements ScoreService {
	
	@EJB
	private ScoreDao scoreDao;

	@Override
	public List<ScoreEntity> findList(int studentId) {
		return scoreDao.findList(studentId);
	}
}
