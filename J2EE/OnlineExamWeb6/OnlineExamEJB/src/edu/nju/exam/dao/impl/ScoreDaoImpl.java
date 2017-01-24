package edu.nju.exam.dao.impl;

import edu.nju.exam.dao.ScoreDao;
import edu.nju.exam.entity.VScoreEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class ScoreDaoImpl implements ScoreDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<VScoreEntity> findList(int studentId) {
		Query query = entityManager.createNativeQuery("SELECT * FROM v_score WHERE sid=? ",VScoreEntity.class);
		query.setParameter(1,studentId);
        return query.getResultList();
	}

}
