package edu.nju.exam.dao;

import java.sql.*;

/**
 * database initialization interface
 */
public interface DataHelper {

    Connection getConnection();

    boolean closeResultSet(ResultSet resultSet);

    boolean closePreparedStatement(PreparedStatement preparedStatement);

    boolean closeConnection(Connection connection);

}
