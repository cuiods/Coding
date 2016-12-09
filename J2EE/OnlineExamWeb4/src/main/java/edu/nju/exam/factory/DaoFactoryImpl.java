package edu.nju.exam.factory;

import edu.nju.exam.dao.ScoreDao;
import edu.nju.exam.dao.StudentDao;
import edu.nju.exam.dao.impl.ScoreDaoImpl;
import edu.nju.exam.dao.impl.StudentDaoImpl;

/**
 * dao factory
 */
public class DaoFactoryImpl implements DaoFactory {

    private static volatile DaoFactoryImpl daoFactory;

    private DaoFactoryImpl() {}

    public static DaoFactoryImpl getInstance() {
        if (daoFactory==null) {
            synchronized (DaoFactoryImpl.class) {
                if (daoFactory==null) {
                    daoFactory = new DaoFactoryImpl();
                }
            }
        }
        return daoFactory;
    }

    public StudentDao getStudentDao() {
        return StudentDaoImpl.getInstance();
    }

    public ScoreDao getScoreDao() {
        return ScoreDaoImpl.getInstance();
    }
}
