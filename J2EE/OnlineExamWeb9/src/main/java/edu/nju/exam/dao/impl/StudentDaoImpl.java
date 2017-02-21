package edu.nju.exam.dao.impl;

import edu.nju.exam.dao.StudentDao;
import edu.nju.exam.entity.StudentEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * impl of studentDao
 */
@Repository
public class StudentDaoImpl implements StudentDao {

    @Resource
    private SessionFactory sessionFactory;

    public StudentEntity find(int studentId) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from StudentEntity where id=:studentId ");
        query.setParameter("studentId",studentId);
        List<StudentEntity> studentEntities = query.list();
        session.close();
        if (studentEntities.size()>0) return studentEntities.get(0);
        return null;
    }

    public StudentEntity find(String studentName) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from StudentEntity where sname=:studentName ");
        query.setParameter("studentName",studentName);
        List<StudentEntity> studentEntities = query.list();
        session.close();
        if (studentEntities.size()>0) return studentEntities.get(0);
        return null;
    }

    public boolean verify(String studentName, String password) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("select count(*) from StudentEntity " +
                "where sname=:studentName and password=:password ");
        query.setParameter("studentName",studentName);
        query.setParameter("password",password);
        List<Long> count = query.list();
        session.close();
        if (count.size()>0 && count.get(0).intValue()>0) {
            return true;
        }
        return false;
    }

}
