package edu.nju.exam.dao.impl;

import edu.nju.exam.dao.DataHelper;
import edu.nju.exam.dao.ScoreDao;
import edu.nju.exam.entity.VScoreEntity;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;

/**
 * impl of scoreDao
 */
public class ScoreDaoImpl implements ScoreDao {

    private static volatile ScoreDaoImpl scoreDao;

    private DataHelper dataHelper;

    private ScoreDaoImpl() {
        dataHelper = DataHelperImpl.getInstance();
    }

    public static ScoreDaoImpl getInstance() {
        if (scoreDao==null) {
            synchronized (ScoreDaoImpl.class) {
                if (scoreDao==null) {
                    scoreDao = new ScoreDaoImpl();
                }
            }
        }
        return scoreDao;
    }

    public List<VScoreEntity> findList(int studentId) {
        Session session = dataHelper.getSession();
        Query query = session.createQuery("from VScoreEntity where sid=:studentId ");
        query.setParameter("studentId",studentId);
        List<VScoreEntity> scoreEntities = query.getResultList();
        dataHelper.closeSession(session);
        return scoreEntities;
    }
}
