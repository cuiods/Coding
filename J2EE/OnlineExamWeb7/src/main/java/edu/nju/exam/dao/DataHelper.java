package edu.nju.exam.dao;

import org.hibernate.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * database initialization interface
 */
public interface DataHelper {

    Session getSession();

    void closeSession(Session session);

}
