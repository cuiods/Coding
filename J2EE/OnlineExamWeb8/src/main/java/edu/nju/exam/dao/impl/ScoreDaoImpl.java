package edu.nju.exam.dao.impl;

import edu.nju.exam.dao.ScoreDao;
import edu.nju.exam.entity.VScoreEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * impl of scoreDao
 */
@Repository
public class ScoreDaoImpl implements ScoreDao {

    @Resource
    private SessionFactory sessionFactory;

    public List<VScoreEntity> findList(int studentId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from VScoreEntity where sid=:studentId ");
        query.setParameter("studentId",studentId);
        List<VScoreEntity> scoreEntities = query.list();
        session.close();
        return scoreEntities;
    }
}
