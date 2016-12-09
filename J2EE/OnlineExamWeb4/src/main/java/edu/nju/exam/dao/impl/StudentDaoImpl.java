package edu.nju.exam.dao.impl;

import edu.nju.exam.dao.DataHelper;
import edu.nju.exam.dao.StudentDao;
import edu.nju.exam.model.StudentEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        Connection connection = dataHelper.getConnection();
        StudentEntity entity = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM student WHERE sid=? ");
            preparedStatement.setInt(1,studentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            entity = packageStudent(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }

    public StudentEntity find(String studentName) {
        Connection connection = dataHelper.getConnection();
        StudentEntity entity = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM student WHERE sname=? ");
            preparedStatement.setString(1,studentName);
            ResultSet resultSet = preparedStatement.executeQuery();
            entity = packageStudent(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }

    public boolean verify(String studentName, String password) {
        Connection connection = dataHelper.getConnection();
        try {
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM student WHERE sname=? AND password=? ");
            statement.setString(1,studentName);
            statement.setString(2,password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet!=null && resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private StudentEntity packageStudent(ResultSet resultSet) throws SQLException {
        StudentEntity entity = null;
        if (resultSet!=null && resultSet.next()) {
            entity = new StudentEntity();
            entity.setStudentId(resultSet.getInt("sid"));
            entity.setStudentName(resultSet.getString("sname"));
            entity.setPassword(resultSet.getString("password"));
        }
        return entity;
    }
}
