package edu.nju.exam.service.impl;

import edu.nju.exam.dao.ScoreDao;
import edu.nju.exam.entity.VScoreEntity;
import edu.nju.exam.service.ScoreService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class ScoreServiceImpl implements ScoreService {
	
	@EJB
	private ScoreDao scoreDao;

	@Override
	public List<VScoreEntity> findList(int studentId) {
		return scoreDao.findList(studentId);
	}
}
