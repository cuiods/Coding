package edu.nju.exam.dao.impl;

import edu.nju.exam.dao.DataHelper;

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

    private DataSource dataSource;

    private DataHelperImpl() {
        try {
            Context context = new InitialContext();
            dataSource = (DataSource) context.lookup("java:comp/env/jdbc/examweb");
        } catch (NamingException e) {
            e.printStackTrace();
        }
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

    public Connection getConnection() {
        assert dataSource!=null;
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public boolean closeResultSet(ResultSet resultSet) {
        try {
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean closePreparedStatement(PreparedStatement preparedStatement) {
        try {
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
