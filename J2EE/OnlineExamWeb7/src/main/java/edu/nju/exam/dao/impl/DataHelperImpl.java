package edu.nju.exam.dao.impl;

import edu.nju.exam.dao.DataHelper;
import edu.nju.exam.entity.CourseEntity;
import edu.nju.exam.entity.ScoreEntity;
import edu.nju.exam.entity.StudentEntity;
import edu.nju.exam.entity.VScoreEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * database access helper
 */
public class DataHelperImpl implements DataHelper {

    private volatile static DataHelperImpl dataHelper;

    private SessionFactory sessionFactory;

    private DataHelperImpl() {
        //1. 创建配置对象
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(CourseEntity.class);
        config.addAnnotatedClass(ScoreEntity.class);
        config.addAnnotatedClass(StudentEntity.class);
        config.addAnnotatedClass(VScoreEntity.class);
        //2. 创建服务注册对象
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
        //3. 创建会话工厂对象
        sessionFactory = config.buildSessionFactory(serviceRegistry);
    }

    public static DataHelperImpl getInstance() {
        if (dataHelper==null) {
            synchronized (DataHelperImpl.class) {
                if (dataHelper==null) {
                    dataHelper = new DataHelperImpl();
                }
            }
        }
        return dataHelper;
    }


    @Override
    public Session getSession() {
        return sessionFactory.openSession();
    }

    @Override
    public void closeSession(Session session) {
        session.close();
    }
}
