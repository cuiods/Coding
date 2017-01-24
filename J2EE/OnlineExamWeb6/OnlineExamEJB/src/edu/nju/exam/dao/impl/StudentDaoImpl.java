package edu.nju.exam.dao.impl;

import edu.nju.exam.dao.StudentDao;
import edu.nju.exam.entity.StudentEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Stateless
public class StudentDaoImpl implements StudentDao {

    @PersistenceContext
    private EntityManager entityManager;

	@Override
	public StudentEntity find(int studentId) {
	    return entityManager.find(StudentEntity.class,studentId);
	}

	@Override
	public StudentEntity find(String studentName) {
        Query query = entityManager.createNativeQuery("SELECT * FROM student WHERE sname=? ",StudentEntity.class);
        query.setParameter(1,studentName);
        List<StudentEntity> entities = query.getResultList();
        if (entities!=null && entities.size()>0) {
            return entities.get(0);
        }
        return null;
	}

	@Override
	public boolean verify(String studentName, String password) {
	    Query query = entityManager.createNativeQuery("SELECT * FROM student WHERE sname=? AND password=? ",StudentEntity.class);
	    query.setParameter(1,studentName);
	    query.setParameter(2,password);
        List list = query.getResultList();
        return list!=null && list.size()>0;
	}

}
