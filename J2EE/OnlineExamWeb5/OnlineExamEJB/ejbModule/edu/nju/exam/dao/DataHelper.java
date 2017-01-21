package edu.nju.exam.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ejb.Local;

/**
 * data helper interface
 * @author cuihao
 *
 */
@Local
public interface DataHelper {
	
	Connection getConnection();

    void closeResult(ResultSet resultSet);

    void closePreparedStatement(PreparedStatement preparedStatement);

    void closeConnection(Connection connection);

}
