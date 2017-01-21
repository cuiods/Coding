package edu.nju.exam.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import edu.nju.exam.dao.DataHelper;

/**
 * Dao helper, used to get connection from dataSource and close connection.
 * @author cuihao
 */
public class DataHelperImpl implements DataHelper{

	private static DataHelperImpl baseDao=new DataHelperImpl();
	
	private InitialContext jndiContext = null;
	private Connection connection = null;
	private DataSource datasource = null;
	
	private DataHelperImpl() {
	}
	
	public static DataHelperImpl getBaseDaoInstance() {
		return baseDao;
	}
	
	@Override
	public Connection getConnection() {
		try {
			final Hashtable<String, String> properties = new Hashtable<String, String>();
			properties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
			try {
				jndiContext = new InitialContext(properties);
				datasource = (DataSource) jndiContext.lookup("java:jboss/datasources/mysqlDS");
				if (datasource==null)
						System.out.println("datasource null");
			} catch (NamingException e) {
				e.printStackTrace();
			}	
			System.out.println("got context");
			System.out.println("About to get ds---DaoHelper");
			connection = datasource.getConnection();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	/*
	 * Connection
	 */
	@Override
	public void closeConnection(Connection con) {
		if(con!=null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * PreparedStatement
	 */
	@Override
	public void closePreparedStatement(PreparedStatement stmt) {
		if(stmt!=null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/*
	 * ResultSet
	 */	
	@Override
	public void closeResult(ResultSet result) {
		if(result!=null) {
			try {
				result.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
