package in.co.rays.project4.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import in.co.rays.project4.beans.CourseBean;
import in.co.rays.project4.exception.ApplicationException;
import in.co.rays.project4.exception.DuplicateException;
import in.co.rays.project4.util.JDBCDataSource;

/**
 * JDBC Implements of course model
 * @author Gagan
 *
 */
public class CourseModel {

	/**
	 * add new course
	 * 
	 * @param b
	 * @return pk
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public static int add(CourseBean bean) throws ApplicationException, DuplicateException {
		int result = 0;
		Connection conn = null;
		PreparedStatement ps = null;

		CourseBean duplicateCourseName = findByName(bean.getCourseName());
		if (duplicateCourseName != null) {
			throw new DuplicateException("Course Name already Exist");
		}
		try {
			conn = JDBCDataSource.getConnection();
			ps = conn.prepareStatement("INSERT INTO ST_COURSE VALUES(?,?,?,?,?,?,?,?)");
			ps.setLong(1, nextPk());
			ps.setString(2, bean.getCourseName());
			ps.setString(3, bean.getDescription());
			ps.setString(4, bean.getDuration());
			ps.setString(5, bean.getCreatedBy());
			ps.setString(6, bean.getModifiedBy());
			ps.setTimestamp(7, bean.getCreatedDatetime());
			ps.setTimestamp(8, bean.getModifiedDatetime());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			// log.debug("EXception in Database...", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add Rollback Exception.." + ex.getMessage());
			}
			throw new ApplicationException("Exception in Course Add method");
		} finally {
			JDBCDataSource.closeConn(conn, ps);
		}
		return result;
	}

	/**
	 * delete course information in table
	 * 
	 * @param b
	 * @throws ApplicationException
	 */
	public static int delete(CourseBean bean) throws ApplicationException {

		int r = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = JDBCDataSource.getConnection();
			ps = conn.prepareStatement("DELETE FROM ST_COURSE WHERE ID = ?");
			ps.setLong(1, bean.getId());
			r = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("Database Exception...", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Exception in Rollback Method" + ex.getMessage());
			}
			throw new ApplicationException("Exception in Delete Method");
		} finally {
			JDBCDataSource.closeConn(conn, ps);
		}
		return r;
	}

	/**
	 * update course information
	 * @param b
	 * @throws ApplicationException
	 */
	public static int update(CourseBean bean) throws ApplicationException {
		int r = 0;

		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(
					"UPDATE ST_COURSE SET COURSE_NAME=?,DESCRIPTION=?,DURATION=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID = ?");
			ps.setLong(8, bean.getId());
			ps.setString(1, bean.getCourseName());
			ps.setString(2, bean.getDescription());
			ps.setString(3, bean.getDuration());
			ps.setString(4, bean.getCreatedBy());
			ps.setString(5, bean.getModifiedBy());
			ps.setTimestamp(6, bean.getCreatedDatetime());
			ps.setTimestamp(7, bean.getModifiedDatetime());

			r = ps.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			// log.debug("Database Exception ...", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : Exception in Rollback.." + ex.getMessage());
			}
			throw new ApplicationException("Exception in Updating the Course Model");
		} finally {
			JDBCDataSource.closeConn(conn, ps);
		}
		return r;
	}

	
	public static void main(String[] args) throws ApplicationException {
		CourseBean b = new CourseBean();
		b.setId(3);
		b.setCourseName("b.com");
		b.setDescription("bachlor of commerce");
		b.setDuration("3 Years");
		update(b);
	}
	
	public static ArrayList<CourseBean> search(CourseBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	/**
	 * search list of course detail
	 * 
	 * @param cbean1
	 * @param pageNo
	 * @param pageSize
	 * @return list
	 * @throws ApplicationException
	 */
	public static ArrayList<CourseBean> search(CourseBean bean, int pageNo, int pageSize) throws ApplicationException {
		ArrayList<CourseBean> list = null;

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COURSE WHERE TRUE");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND ID=" + bean.getId());
			}
			if (bean.getCourseName() != null && bean.getCourseName().length() > 0) {
				sql.append(" AND COURSE_NAME LIKE '" + bean.getCourseName() + "%'");
			}
			if (bean.getDescription() != null && bean.getDescription().length() > 0) {
				sql.append(" AND DESCRIPTION LIKE '%" + bean.getDescription() + "%'");
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
			list = new ArrayList<CourseBean>();
			while (rs.next()) {
				bean = new CourseBean();
				bean.setId(rs.getLong(1));
				bean.setCourseName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setDuration(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
				list.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("Database Exception ,,,," , e);
			throw new ApplicationException("Exception in the Search Method" + e.getMessage());

		} finally {
			JDBCDataSource.closeConn(conn, ps, rs);
		}
		return list;
	}

	public static ArrayList<CourseBean> list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 * to show course list
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return list
	 * @throws ApplicationException
	 */
	public static ArrayList<CourseBean> list(int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COURSE WHERE TRUE");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" Limit " + pageNo + ", " + pageSize);
		}

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<CourseBean> l = null;
		try {

			conn = JDBCDataSource.getConnection();

			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			CourseBean bean = null;
			l = new ArrayList<CourseBean>();
			while (rs.next()) {
				bean = new CourseBean();
				bean.setId(rs.getLong(1));
				bean.setCourseName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setDuration(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
				l.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("Database Exception in list ...",e);
			throw new ApplicationException("Exception : Exception in CourseModel List method " + e.getMessage());
		} finally {
			JDBCDataSource.closeConn(conn, ps, rs);
		}
		return l;

	}

	/**
	 * find course by name
	 * 
	 * @param courseName
	 * @return bean
	 * @throws ApplicationException
	 */
	public static CourseBean findByName(String name) throws ApplicationException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		CourseBean bean = null;
		try {

			conn = JDBCDataSource.getConnection();
			ps = conn.prepareStatement("SELECT * FROM ST_COURSE WHERE NAME=?");
			ps.setString(1, name);
			rs = ps.executeQuery();
			bean = new CourseBean();
			while (rs.next()) {
				bean.setId(rs.getLong(1));
				bean.setCourseName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setDuration(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
			}
		} catch (Exception e) {
//			e.printStackTrace();
			// log.debug("Database Exception ..", e);
			 throw new ApplicationException("Exception in Course Model FindByName Method ");
		} finally {
			JDBCDataSource.closeConn(conn, ps, rs);
		}
		return bean;
	}

	/**
	 * find information by pk
	 * 
	 * @param pk
	 * @return bean
	 * @throws ApplicationException
	 */
	public static CourseBean findByPK(long pk) throws ApplicationException {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		CourseBean bean = null;
		try {

			conn = JDBCDataSource.getConnection();
			ps = conn.prepareStatement("SELECT * FROM ST_COURSE WHERE ID=?");
			ps.setLong(1, pk);
			rs = ps.executeQuery();
			bean = new CourseBean();
			while (rs.next()) {
				bean.setId(rs.getLong(1));
				bean.setCourseName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setDuration(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
			}
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("DatabaseException ... ", e);
			throw new ApplicationException("Exception : Exception in the findbyPk method");
		} finally {
			JDBCDataSource.closeConn(conn, ps, rs);
		}
		return bean;
	}

	/**
	 * create id
	 * 
	 * @return pk
	 * @throws DatabaseException
	 */
	private static long nextPk() throws Exception {
		long id = 0;

		Connection conn = null;
		Statement s = null;
		ResultSet r = null;
		try {

			conn = JDBCDataSource.getConnection();

			s = conn.createStatement();

			r = s.executeQuery("SELECT MAX(ID) FROM ST_COURSE");

			while (r.next()) {
				id = r.getLong(1);
			}
		} finally {
			r.close();
			s.close();
			conn.close();
		}
		return id + 1;
	}

}
