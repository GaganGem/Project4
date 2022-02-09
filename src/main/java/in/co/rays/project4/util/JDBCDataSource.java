package in.co.rays.project4.util;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * JDBC DataSource is a Data Connection Pool
 * @author Gagan
 *
 */
public final class JDBCDataSource {
	
	/**
	 * JDBC Database connection pool ( DCP )
	 */
	private static JDBCDataSource jdbc = null;
	private ComboPooledDataSource cp = null;
	ResourceBundle rb = ResourceBundle.getBundle("resourceBundle/res");	


	private JDBCDataSource() {
		cp = new ComboPooledDataSource();
		try {
			cp.setDriverClass(rb.getString("driver"));
			cp.setJdbcUrl(rb.getString("url"));
			cp.setUser(rb.getString("user"));
			cp.setPassword(rb.getString("pwd"));
			cp.setInitialPoolSize(2);
			cp.setAcquireIncrement(2);
			cp.setMaxPoolSize(10);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create instance of Connection Pool
	 *
	 * @return datasource
	 */
	public static JDBCDataSource getInstance() {
		if (jdbc == null) {
			jdbc = new JDBCDataSource();
		}
		return jdbc;
	}
	
	/**
	 * Gets the connection from ComboPooledDataSource
	 *
	 * @return connection
	 */
	public static Connection getConnection() {
		try {
			return getInstance().cp.getConnection();
		} catch (SQLException e) {
			return null;
		}
	}

	/**
	 * Closes a connection
	 *
	 * @param connection
	 * @throws Exception
	 */
	public static void closeConn(Connection con, PreparedStatement ps, ResultSet rs) {
		try {
			if (rs != null)rs.close();
			if (ps != null)ps.close();
			if (con != null)con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void closeConn(Connection con, PreparedStatement ps) {
		try {
			if (ps != null)ps.close();
			if (con != null)con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void closeConn(Connection con) {
		try {
			if (con != null)con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
