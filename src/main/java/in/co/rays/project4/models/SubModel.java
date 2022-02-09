package in.co.rays.project4.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.project4.beans.CourseBean;
import in.co.rays.project4.beans.SubjectBean;
import in.co.rays.project4.exception.ApplicationException;
import in.co.rays.project4.exception.DuplicateException;
import in.co.rays.project4.util.JDBCDataSource;

/**
 * JDBC Implement of subject
 * 
 * @author Gagan
 *
 */
public class SubModel {

	/**
	 * create id
	 * 
	 * @return pk
	 * @throws DatabaseException
	 */
	public static Integer nextPk() throws Exception {
		Connection conn = null;
		int pk = 0;

		conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(id) FROM ST_SUBJECT");
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			pk = rs.getInt(1);
		}
		JDBCDataSource.closeConn(conn, pstmt, rs);
		return pk + 1;
	}

	/**
	 * add subject
	 * 
	 * @param bean
	 * @return pk
	 * @throws DuplicateException
	 * @throws ApplicationException
	 */
	public static Long add(SubjectBean bean) throws DuplicateException, ApplicationException {

		Connection conn = null;
		long pk = 0;

		CourseBean cBean = CourseModel.findByPK(bean.getCourseId());
		bean.setCourseName(cBean.getCourseName());

		SubjectBean duplicateSubjectName = findByName(bean.getSubName());

		if (duplicateSubjectName != null) {
			throw new DuplicateException("Subject Name Already Exists");

		}

		try {
			pk = nextPk();

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ST_SUBJECT VALUES(?,?,?,?,?,?,?,?,?)");
			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getSubName());
			pstmt.setLong(3, bean.getCourseId());
			pstmt.setString(4, bean.getCourseName());
			pstmt.setString(5, bean.getDescription());
			pstmt.setString(6, bean.getCreatedBy());
			pstmt.setString(7, bean.getModifiedBy());
			pstmt.setTimestamp(8, bean.getCreatedDatetime());
			pstmt.setTimestamp(9, bean.getModifiedDatetime());
			pstmt.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			// log.error("Database Exception..", e);
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception:add rollback exception" + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add Subject");
		} finally {
			JDBCDataSource.closeConn(conn);
		}
		return pk;

	}
	
	public static void main(String[] args) throws DuplicateException, ApplicationException {
		SubjectBean b = new SubjectBean();
//		b.setCourseId(2);
		b.setSubName("ga");
//		b.setDescription("fa");
//		add(b);
		
		findByName("e");
		System.out.println("fdk");
	}

	/**
	 * delete subject
	 * 
	 * @param bean
	 * @throws ApplicationException
	 */
	public static void delete(SubjectBean bean) throws Exception {
		Connection conn = null;
		conn = JDBCDataSource.getConnection();
		conn.setAutoCommit(false);
		PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_SUBJECT WHERE ID=?");
		pstmt.setLong(1, bean.getId());
		pstmt.executeUpdate();
		conn.commit();

		JDBCDataSource.closeConn(conn, pstmt);
	}

	/**
	 * update subject
	 * 
	 * @param bean
	 * @throws ApplicationException
	 * @throws DuplicateException
	 */
	public static void update(SubjectBean bean) throws ApplicationException, DuplicateException {
		Connection conn = null;
		CourseBean cBean = CourseModel.findByPK(bean.getCourseId());
		bean.setCourseName(cBean.getCourseName());

		SubjectBean beanExist = findByName(bean.getSubName());

		if (beanExist != null && beanExist.getId() != bean.getId()) {
			throw new DuplicateException("Subject is already exist");

		}
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ST_SUBJECT SET SUB_NAME=?,COURSE_ID=?,COURSE_NAME=?,Description=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");

			pstmt.setString(1, bean.getSubName());
			pstmt.setLong(2, bean.getCourseId());
			pstmt.setString(4, bean.getDescription());
			pstmt.setString(3, bean.getCourseName());
			pstmt.setString(5, bean.getCreatedBy());
			pstmt.setString(6, bean.getModifiedBy());
			pstmt.setTimestamp(7, bean.getCreatedDatetime());
			pstmt.setTimestamp(8, bean.getModifiedDatetime());
			pstmt.setLong(9, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			// log.error("Database Exception..",e);

			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception:delete rollback exception" + ex.getMessage());

			}
			throw new ApplicationException("Exception in updating Subject");
		} finally {
			JDBCDataSource.closeConn(conn);
		}
	}

	/**
	 * find subject by name
	 * 
	 * @param subjectname
	 * @return bean
	 * @throws ApplicationException
	 */
	public static SubjectBean findByName(String name) throws ApplicationException {
		Connection conn = null;
		SubjectBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM ST_SUBJECT WHERE SUB_NAME=?");
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new SubjectBean();
				bean.setId(rs.getLong(1));
				bean.setSubName(rs.getString(2));
				bean.setDescription(rs.getString(5));
				bean.setCourseId(rs.getLong(3));
				bean.setCourseName(rs.getString(4));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}
		} catch (Exception e) {
			// log.error("Database Exception..",e);
			e.printStackTrace();
			throw new ApplicationException("Exception:Exception in getting Subject by Name");

		} finally {
			JDBCDataSource.closeConn(conn);
		}
		return bean;
	}

	/**
	 * find subject by pk
	 * 
	 * @param pk
	 * @return bean
	 * @throws ApplicationException
	 */
	public static SubjectBean findByPk(long pk) throws ApplicationException {
		Connection conn = null;
		SubjectBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM ST_SUBJECT WHERE ID=?");
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new SubjectBean();

				bean.setId(rs.getLong(1));
				bean.setSubName(rs.getString(2));
				bean.setDescription(rs.getString(5));
				bean.setCourseId(rs.getLong(3));
				bean.setCourseName(rs.getString(4));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}
		} catch (Exception e) {
			// log.error("Database Exception..",e);
			throw new ApplicationException("Exception:Exception in getting Subject by pk");

		} finally {
			JDBCDataSource.closeConn(conn);
		}
		return bean;
	}

	public static List<SubjectBean> search(SubjectBean bean) throws Exception {
		return search(bean, 0, 0);
	}

	/**
	 * search subject
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @return list
	 * @throws ApplicationException
	 */
	public static List<SubjectBean> search(SubjectBean bean, int pageNo, int pageSize) throws Exception {
		ArrayList<SubjectBean> list = null;

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_SUBJECT WHERE TRUE");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND ID=" + bean.getId());
			}
			if (bean.getSubName() != null && bean.getSubName().length() > 0) {
				sql.append(" AND SUB_NAME LIKE '" + bean.getSubName() + "%'");
			}
			if (bean.getCourseId() > 0) {
				sql.append(" AND COURSE_ID=" + bean.getCourseId());
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" Limit " + pageNo + ", " + pageSize);
		}
		Connection conn = null;
		list = new ArrayList<SubjectBean>();
		conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			bean = new SubjectBean();

			bean.setId(rs.getLong(1));
			bean.setSubName(rs.getString(2));
			bean.setDescription(rs.getString(5));
			bean.setCourseId(rs.getLong(3));
			bean.setCourseName(rs.getString(4));
			bean.setCreatedBy(rs.getString(6));
			bean.setModifiedBy(rs.getString(7));
			bean.setCreatedDatetime(rs.getTimestamp(8));
			bean.setModifiedDatetime(rs.getTimestamp(9));
			list.add(bean);
		}
		rs.close();

		JDBCDataSource.closeConn(conn, pstmt, rs);
		return list;
	}

	public static List list() throws Exception {
		return list(0, 0);
	}

	/**
	 * list of subject
	 * @param pageNo
	 * @param pageSize
	 * @return list
	 * @throws ApplicationException
	 */
	public static List list(int pageNo, int pageSize) throws Exception {
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_SUBJECT ");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + " , " + pageSize);
		}

		Connection conn = null;
		ArrayList list = new ArrayList();
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				SubjectBean bean = new SubjectBean();

				bean.setId(rs.getLong(1));
				bean.setSubName(rs.getString(2));
				bean.setDescription(rs.getString(5));
				bean.setCourseId(rs.getLong(3));
				bean.setCourseName(rs.getString(4));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("database Exception....", e);
			// throw new ApplicationException("Exception in list Method of
			// Subject Model");
		} finally {
			JDBCDataSource.closeConn(conn);
		}
		// log.debug("Subject Model list method End");
		return list;
	}
}
