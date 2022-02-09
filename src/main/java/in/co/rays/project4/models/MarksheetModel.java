package in.co.rays.project4.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.project4.beans.CourseBean;
import in.co.rays.project4.beans.MarksheetBean;
import in.co.rays.project4.beans.StudentBean;
import in.co.rays.project4.exception.ApplicationException;
import in.co.rays.project4.exception.DataBaseException;
import in.co.rays.project4.exception.DuplicateException;
import in.co.rays.project4.util.JDBCDataSource;

/**
 * JDBC Implements of marksheet model
 * @author Gagan
 *
 */
public class MarksheetModel {

	/**
	 * add new marksheet
	 * @param bean
	 * @return pk
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public static int add(MarksheetBean bean) throws ApplicationException, DuplicateException {
		int result = 0;

		StudentBean studentbean;
		try {
			studentbean = StudentModel.findByPK(bean.getStudentId());
			bean.setName(studentbean.getFirstName() + " " + studentbean.getLastName());

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		MarksheetBean duplicateMarksheet = findByRollNo(bean.getRollNo());

		if (duplicateMarksheet.getId() > 0) {
			throw new DuplicateException("Roll Number already exists");
		}

		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = JDBCDataSource.getConnection();
			ps = conn.prepareStatement("INSERT INTO ST_MARKSHEET VALUES(?,?,?,?,?,?,?,?,?,?,?)");
			ps.setLong(1, nextPk());
			ps.setString(2, bean.getRollNo());
			ps.setLong(3, bean.getStudentId());
			ps.setString(4, bean.getName());
			ps.setInt(5, bean.getPhysics());
			ps.setInt(6, bean.getChemistry());
			ps.setInt(7, bean.getMaths());
			ps.setString(8, bean.getCreatedBy());
			ps.setString(9, bean.getModifiedBy());
			ps.setTimestamp(10, bean.getCreatedDatetime());
			ps.setTimestamp(11, bean.getModifiedDatetime());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in add marksheet");
		} finally {
			JDBCDataSource.closeConn(conn, ps);
		}
		return result;
	}

	/**
	 * delete marksheet information
	 * @param bean
	 * @throws ApplicationException
	 */
	public static int delete(MarksheetBean bean) throws ApplicationException {

		int r = 0;

		Connection conn = null;
		PreparedStatement ps = null;
		try {

			conn = JDBCDataSource.getConnection();
			ps = conn.prepareStatement("DELETE FROM ST_MARKSHEET WHERE ID = ?");
			ps.setLong(1, bean.getId());
			r = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error(e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				// log.error(ex);
				throw new ApplicationException("Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in delete marksheet");
		} finally {
			JDBCDataSource.closeConn(conn, ps);
		}
		return r;
	}

	/**
	 * update marksheet information
	 * @param bean
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public static int update(MarksheetBean bean) throws ApplicationException {
		int r = 0;

		StudentBean studentbean;
		try {
			studentbean = StudentModel.findByPK(bean.getStudentId());
			bean.setName(studentbean.getFirstName() + " " + studentbean.getLastName());

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} // method

		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = JDBCDataSource.getConnection();

			ps = conn.prepareStatement(
					"UPDATE ST_MARKSHEET SET ROLL_NO=?,STUDENT_ID=?,NAME=?,PHYSICS=?,CHEMISTRY=?,MATHS=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ROLL_NO = ?");
			ps.setString(1, bean.getRollNo());
			ps.setLong(2, bean.getStudentId());
			ps.setString(3, bean.getName());
			ps.setInt(4, bean.getPhysics());
			ps.setInt(5, bean.getChemistry());
			ps.setInt(6, bean.getMaths());
			ps.setString(7, bean.getCreatedBy());
			ps.setString(8, bean.getModifiedBy());
			ps.setTimestamp(9, bean.getCreatedDatetime());
			ps.setTimestamp(10, bean.getModifiedDatetime());
			ps.setString(11, bean.getRollNo());

		} catch (Exception e) {
			e.printStackTrace();
			// log.error(e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Update rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating Marksheet ");
		} finally {

			JDBCDataSource.closeConn(conn, ps);
		}
		return r;
	}

	public static List<MarksheetBean> search(MarksheetBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	/**
	 * search marksheet
	 * @param marksheet
	 * @param pageNo
	 * @param pageSize
	 * @return list
	 * @throws ApplicationException
	 */	
	public static List<MarksheetBean> search(MarksheetBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer(
				"SELECT ROLL_NO,STUDENT_ID,NAME,PHYSICS, CHEMISTRY, MATHS, CREATED_BY, MODIFIED_BY, CREATED_DATETIME, MODIFIED_DATETIME,ID FROM ST_MARKSHEET WHERE TRUE");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND ID=" + bean.getId());
			}
			if (bean.getRollNo() != null && bean.getRollNo().length() > 0) {
				sql.append(" AND ROLL_NO LIKE '" + bean.getRollNo() + "%'");
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
		ArrayList<MarksheetBean> list = null;
		try {

			list = new ArrayList<MarksheetBean>();
			conn = JDBCDataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());

			rs = ps.executeQuery();
			while (rs.next()) {
				bean = new MarksheetBean();
				bean.setRollNo(rs.getString(1));
				bean.setStudentId(rs.getLong(2));
				bean.setName(rs.getString(3));
				bean.setPhysics(rs.getInt(4));
				bean.setChemistry(rs.getInt(5));
				bean.setMaths(rs.getInt(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
				bean.setId(rs.getLong(11));
				list.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// log.error(e);
			throw new ApplicationException("Update rollback exception " + e.getMessage());
		} finally {
			JDBCDataSource.closeConn(conn, ps, rs);
		}
		return list;
	}

	public static ArrayList<MarksheetBean> list() throws ApplicationException {
		return list(0,0);
	}

	/**
	 * get List of Marksheet with pagination
	 *
	 * @return list : List of Marksheets
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * @throws DatabaseException
	 */
	public static ArrayList<MarksheetBean> list(int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer(
				"SELECT ROLL_NO,STUDENT_ID,NAME,PHYSICS, CHEMISTRY, MATHS, CREATED_BY, MODIFIED_BY, CREATED_DATETIME, MODIFIED_DATETIME,ID FROM ST_MARKSHEET");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" Limit " + pageNo + ", " + pageSize);
		}

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<MarksheetBean> l = null;
		try {

			conn = JDBCDataSource.getConnection();

			ps = conn.prepareStatement(sql.toString());
			l = new ArrayList<MarksheetBean>();
			rs = ps.executeQuery();
			while (rs.next()) {
				MarksheetBean bean = new MarksheetBean();
				bean.setRollNo(rs.getString(1));
				bean.setStudentId(rs.getLong(2));
				bean.setName(rs.getString(3));
				bean.setPhysics(rs.getInt(4));
				bean.setChemistry(rs.getInt(5));
				bean.setMaths(rs.getInt(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
				bean.setId(rs.getLong(11));
				l.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// log.error(e);
			throw new ApplicationException("Exception in getting list of Marksheet");
		} finally {
			JDBCDataSource.closeConn(conn, ps, rs);
		}
		return l;

	}

	/**
	 * get merit list
	 * @param pageNo
	 * @param pageSize
	 * @return list
	 * @throws ApplicationException
	 */
	public static ArrayList<MarksheetBean> meritList() throws ApplicationException {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<MarksheetBean> l = null;
		try {

			conn = JDBCDataSource.getConnection();

			ps = conn.prepareStatement(
					"SELECT ROLL_NO,STUDENT_ID,NAME,PHYSICS, CHEMISTRY, MATHS, CREATED_BY, MODIFIED_BY, CREATED_DATETIME, MODIFIED_DATETIME,ID,(PHYSICS+CHEMISTRY+MATHS) as total FROM ST_MARKSHEET ORDER BY total DESC");
			l = new ArrayList<MarksheetBean>();
			rs = ps.executeQuery();
			while (rs.next()) {
				MarksheetBean bean = new MarksheetBean();
				bean.setRollNo(rs.getString(1));
				bean.setStudentId(rs.getLong(2));
				bean.setName(rs.getString(3));
				bean.setPhysics(rs.getInt(4));
				bean.setChemistry(rs.getInt(5));
				bean.setMaths(rs.getInt(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
				bean.setId(rs.getLong(11));
				l.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// log.error(e);
			throw new ApplicationException("Exception in getting merit list of Marksheet");
		} finally {
			JDBCDataSource.closeConn(conn, ps, rs);
		}
		return l;

	}

	/**
	 * find information with the help of rollno
	 * @param rollNo
	 * @return bean
	 * @throws ApplicationException
	 */
	public static MarksheetBean findByRollNo(String rollNo) throws ApplicationException {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		MarksheetBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			ps = conn.prepareStatement("SELECT * FROM ST_MARKSHEET WHERE ROLL_NO=?");
			ps.setString(1, rollNo);
			rs = ps.executeQuery();
			bean = new MarksheetBean();
			while (rs.next()) {
				bean.setId(rs.getLong(1));
				bean.setRollNo(rs.getString(2));
				bean.setStudentId(rs.getLong(3));
				bean.setName(rs.getString(4));
				bean.setPhysics(rs.getInt(5));
				bean.setChemistry(rs.getInt(6));
				bean.setMaths(rs.getInt(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
			}
		} catch (Exception e) {
			e.printStackTrace();
			// log.error(e);
			throw new ApplicationException("Exception in getting marksheet by pk");
		} finally {
			JDBCDataSource.closeConn(conn, ps, rs);
		}
		return bean;
	}

	/**
	 * find information with the help of pk
	 * @param pk
	 * @return bean
	 * @throws ApplicationException
	 */
	public static MarksheetBean findByPK(long id) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		MarksheetBean bean = null;
		try {

			conn = JDBCDataSource.getConnection();
			ps = conn.prepareStatement("SELECT * FROM ST_MARKSHEET WHERE ID=?");
			ps.setLong(1, id);
			rs = ps.executeQuery();
			bean = new MarksheetBean();
			while (rs.next()) {
				bean.setId(rs.getLong(1));
				bean.setRollNo(rs.getString(2));
				bean.setStudentId(rs.getLong(3));
				bean.setName(rs.getString(4));
				bean.setPhysics(rs.getInt(5));
				bean.setChemistry(rs.getInt(6));
				bean.setMaths(rs.getInt(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
			}
		} finally {
			JDBCDataSource.closeConn(conn, ps, rs);
		}
		return bean;
	}

	/**
	 * add new id
	 * @return pk
	 * @throws DatabaseException
	 */
	private static long nextPk() throws DataBaseException {
		long id = 0;

		Connection conn = null;
		Statement s = null;
		ResultSet r = null;
		try {

			conn = JDBCDataSource.getConnection();

			s = conn.createStatement();

			r = s.executeQuery("SELECT MAX(ID) FROM ST_MARKSHEET");

			while (r.next()) {
				id = r.getLong(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// log.error(e);
			// throw new DatabaseException("Exception in Marksheet getting PK");
		} finally {
			JDBCDataSource.closeConn(conn);
			try {
				s.close();
				r.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return id + 1;
	}

	public static void main(String[] args) throws Exception {

		MarksheetBean b = new MarksheetBean();
		// b.setId(1);
		b.setRollNo("A101");
		// b.setStudentId(2);
		b.setName("gagan");
		b.setPhysics(45);
		b.setChemistry(93);
		b.setMaths(46);
		b.setCreatedBy("gagan");
		b.setModifiedBy("gagan");

		System.out.println(MarksheetModel.add(b));

	}

}
