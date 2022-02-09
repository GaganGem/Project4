package in.co.rays.project4.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.project4.beans.RoleBean;
import in.co.rays.project4.exception.ApplicationException;
import in.co.rays.project4.util.JDBCDataSource;

/**
 * JDBC Implement of role model
 * @author Gagan
 *
 */
public class RoleModel {

	/**
	 * add new role 
	 * @param bean
	 * @return pk
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public static int add(RoleBean bean) throws ApplicationException {
		int result = 0;
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = JDBCDataSource.getConnection();
			ps = conn.prepareStatement("INSERT INTO ST_ROLE VALUES(?,?,?,?,?,?,?)");
			ps.setLong(1, nextPk());
			ps.setString(2, bean.getName());
			ps.setString(3, bean.getDescription());
			ps.setString(4, bean.getCreatedBy());
			ps.setString(5, bean.getModifiedBy());
			ps.setTimestamp(6, bean.getCreatedDatetime());
			ps.setTimestamp(7, bean.getModifiedDatetime());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("DataException.", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception: add rollback exception" + ex.getMessage());
			}
			throw new ApplicationException("Exception: Exception in add Role");
		} finally {
			JDBCDataSource.closeConn(conn, ps);

		}

		return result;
	}

	/**
	 * delete role
	 * @param bean
	 * @throws ApplicationException
	 */
	public static int delete(RoleBean bean) throws Exception {

		int r = 0;

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);// Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_ROLE WHERE ID=?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit();// End transaction
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception:Delete rollback exception" + ex.getMessage());
			}
			throw new ApplicationException("Exception: Exception in deleted role");
		} finally {
			JDBCDataSource.closeConn(conn);
		}
		return r;
	}

	/**
	 * update role 
	 * @param bean
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public static int update(RoleBean bean) throws ApplicationException {
		int r = 0;
		Connection conn = null;
		PreparedStatement ps = null;

		try {

			conn = JDBCDataSource.getConnection();

			ps = conn.prepareStatement(
					"UPDATE ST_ROLE SET NAME=?,DESCRIPTION=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID = ?");
			ps.setLong(7, bean.getId());
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getDescription());
			ps.setString(3, bean.getCreatedBy());
			ps.setString(4, bean.getModifiedBy());
			ps.setTimestamp(5, bean.getCreatedDatetime());
			ps.setTimestamp(6, bean.getModifiedDatetime());

			ps.executeUpdate();
			r = 1;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in Updating Role");
		} finally {
			JDBCDataSource.closeConn(conn);
		}
		return r;
	}

	/**
	 * search role
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @return list
	 * @throws ApplicationException
	 */
	public static List<RoleBean> search(RoleBean bean, int pageNo, int pageSize) throws ApplicationException {
		ArrayList<RoleBean> list = null;

		StringBuffer sql = new StringBuffer(
				"SELECT ID,NAME,DESCRIPTION,CREATED_BY, MODIFIED_BY, CREATED_DATETIME, MODIFIED_DATETIME FROM ST_ROLE WHERE TRUE");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND ID=" + bean.getId());
			}
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND NAME LIKE '" + bean.getName() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" Limit " + pageNo + ", " + pageSize);
		}

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			conn = JDBCDataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			list = new ArrayList<RoleBean>();
			while (rs.next()) {
				bean = new RoleBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreatedDatetime(rs.getTimestamp(6));
				bean.setModifiedDatetime(rs.getTimestamp(7));
				list.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConn(conn, ps, rs);
		}
		return list;
	}

	public static List<RoleBean> search(RoleBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	public static List<RoleBean> list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 *list of role
	 * @param pageNo
	 * @param pageSize
	 * @return list
	 * @throws ApplicationException
	 */
	public static List<RoleBean> list(int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer(
				"SELECT ID,NAME,DESCRIPTION, CREATED_BY, MODIFIED_BY, CREATED_DATETIME, MODIFIED_DATETIME FROM ST_ROLE");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" Limit " + pageNo + ", " + pageSize);
		}
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<RoleBean> l = new ArrayList<RoleBean>();

		try {
			conn = JDBCDataSource.getConnection();

			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			RoleBean bean = null;
			while (rs.next()) {
				bean = new RoleBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreatedDatetime(rs.getTimestamp(6));
				bean.setModifiedDatetime(rs.getTimestamp(7));
				l.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConn(conn, ps, rs);

		}
		return l;

	}

	/**
	 * find role with the help of name
	 * @param name
	 * @return bean
	 * @throws ApplicationException
	 */
	public RoleBean findByName(String name) throws Exception {
		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM ST_ROLE WHERE NAME=?");
		ps.setString(1, name);
		ResultSet rs = ps.executeQuery();
		RoleBean bean = new RoleBean();
		while (rs.next()) {
			bean.setId(rs.getLong(1));
			bean.setName(rs.getString(2));
			bean.setDescription(rs.getString(3));
			bean.setCreatedBy(rs.getString(4));
			bean.setModifiedBy(rs.getString(5));
			bean.setCreatedDatetime(rs.getTimestamp(6));
			bean.setModifiedDatetime(rs.getTimestamp(7));
		}
		JDBCDataSource.closeConn(conn, ps, rs);

		return bean;
	}

	/**
	 * find by role with the help of role
	 * @param pk
	 * @return bean
	 * @throws ApplicationException
	 */
	public static RoleBean findByPK(long id) throws Exception {
		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM ST_ROLE WHERE ID=?");
		ps.setLong(1, id);
		ResultSet rs = ps.executeQuery();
		RoleBean bean = new RoleBean();
		while (rs.next()) {
			bean.setId(rs.getLong(1));
			bean.setName(rs.getString(2));
			bean.setDescription(rs.getString(3));
			bean.setCreatedBy(rs.getString(4));
			bean.setModifiedBy(rs.getString(5));
			bean.setCreatedDatetime(rs.getTimestamp(6));
			bean.setModifiedDatetime(rs.getTimestamp(7));
		}
		JDBCDataSource.closeConn(conn, ps, rs);
		return bean;
	}

	/**
	 * create id 
	 * @return pk
	 * @throws DatabaseException
	 */
	private static long nextPk() throws Exception {
		long id = 0;

		Connection conn = JDBCDataSource.getConnection();

		Statement s = conn.createStatement();

		ResultSet r = s.executeQuery("SELECT MAX(ID) FROM ST_ROLE");

		while (r.next()) {
			id = r.getLong(1);
		}
		r.close();
		s.close();
		conn.close();

		return id + 1;
	}

}
