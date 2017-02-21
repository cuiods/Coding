package edu.nju.exam.factory;

import edu.nju.exam.dao.ScoreDao;
import edu.nju.exam.dao.StudentDao;

/**
 * dao factory
 */
public interface DaoFactory {
    StudentDao getStudentDao();
    ScoreDao getScoreDao();
}
