package edu.nju.exam.dao.impl;

import edu.nju.exam.dao.DataHelper;
import edu.nju.exam.dao.StudentDao;
import edu.nju.exam.entity.StudentEntity;
import org.hibernate.Session;

import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;

/**
 * impl of studentDao
 */
public class StudentDaoImpl implements StudentDao {

    private DataHelper dataHelper;

    private static volatile StudentDaoImpl studentDao;

    private StudentDaoImpl() {
        dataHelper = DataHelperImpl.getInstance();
    }

    public static StudentDaoImpl getInstance() {
        if (studentDao==null) {
            synchronized (StudentDaoImpl.class) {
                if (studentDao==null) {
                    studentDao = new StudentDaoImpl();
                }
            }
        }
        return studentDao;
    }

    public StudentEntity find(int studentId) {
        Session session = dataHelper.getSession();
        Query query = session.createQuery("from StudentEntity where id=:studentId ");
        query.setParameter("studentId",studentId);
        List<StudentEntity> studentEntities = query.getResultList();
        dataHelper.closeSession(session);
        if (studentEntities.size()>0) return studentEntities.get(0);
        return null;
    }

    public StudentEntity find(String studentName) {
        Session session = dataHelper.getSession();
        Query query = session.createQuery("from StudentEntity where sname=:studentName ");
        query.setParameter("studentName",studentName);
        List<StudentEntity> studentEntities = query.getResultList();
        dataHelper.closeSession(session);
        if (studentEntities.size()>0) return studentEntities.get(0);
        return null;
    }

    public boolean verify(String studentName, String password) {
        Session session = dataHelper.getSession();
        Query query = session.createQuery("select count(*) from StudentEntity " +
                "where sname=:studentName and password=:password ");
        query.setParameter("studentName",studentName);
        query.setParameter("password",password);
        List<Long> count = query.getResultList();
        dataHelper.closeSession(session);
        if (count.size()>0 && count.get(0).intValue()>0) {
            return true;
        }
        return false;
    }

}
