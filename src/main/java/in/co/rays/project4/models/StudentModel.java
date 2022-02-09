package in.co.rays.project4.models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import in.co.rays.project4.beans.CollegeBean;
import in.co.rays.project4.beans.StudentBean;
import in.co.rays.project4.exception.ApplicationException;
import in.co.rays.project4.exception.DuplicateException;
import in.co.rays.project4.util.HTMLUtility;
import in.co.rays.project4.util.JDBCDataSource;
import in.co.rays.project4.exception.DataBaseException;
import in.co.rays.project4.exception.DuplicateException;

/**
 * JDBC Implement of student model
 * 
 * @author Gagan
 *
 */
public class StudentModel {

	/**
	 * add student
	 * 
	 * @param bean
	 * @return pk
	 * @throws DuplicateRecordException
	 * @throws ApplicationException
	 * @throws DuplicateException 
	 */
	public static int add(StudentBean bean) throws ApplicationException, DuplicateException {
		int r = 0;

		Connection conn = null;
		PreparedStatement ps = null;
		CollegeBean collegeBean = CollegeModel.findByPK(bean.getCollegeId());

		StudentBean duplicateName = findByEmailId(bean.getEmail());

		if (duplicateName.getId() > 0) {
			throw new DuplicateException("Email already exists");
		}
		try {
			conn = JDBCDataSource.getConnection();
			ps = conn.prepareStatement("INSERT INTO ST_STUDENT VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setLong(1, nextPk());
			ps.setLong(2, bean.getCollegeId());
			ps.setString(3, collegeBean.getName());
			ps.setString(4, bean.getFirstName());
			ps.setString(5, bean.getLastName());
			ps.setDate(6, new Date(bean.getDob().getTime()));
			ps.setString(7, bean.getMobileNo());
			ps.setString(8, bean.getEmail());
			ps.setString(9, bean.getCreatedBy());
			ps.setString(10, bean.getModifiedBy());
			ps.setTimestamp(11, bean.getCreatedDatetime());
			ps.setTimestamp(12, bean.getModifiedDatetime());
			r = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();

			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception: add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception:Exception in add student");

		} finally {
			JDBCDataSource.closeConn(conn, ps);

		}

		return r;
	}

	/**
	 * delete student
	 * 
	 * @param bean
	 * @throws ApplicationException
	 */
	public static int delete(StudentBean bean) throws Exception {

		int r = 0;

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement("DELETE FROM ST_STUDENT WHERE ID = ?");
		ps.setLong(1, bean.getId());
		r = ps.executeUpdate();

		JDBCDataSource.closeConn(conn, ps);

		return r;
	}

	/**
	 * update student
	 * @param bean
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public static int update(StudentBean bean) throws ApplicationException {
		int r = 0;
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = JDBCDataSource.getConnection();

			ps = conn.prepareStatement(
					"UPDATE ST_STUDENT SET COLLEGE_ID=?,COLLEGE_NAME=?,FIRST_NAME=?,LAST_NAME=?,DATE_OF_BIRTH=?,MOBILE_NO=?,EMAIL=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID = ?");
			ps.setLong(12, bean.getId());
			ps.setLong(1, bean.getCollegeId());
			ps.setString(2, bean.getCollegeName());
			ps.setString(3, bean.getFirstName());
			ps.setString(4, bean.getLastName());
			ps.setDate(5, new Date(bean.getDob().getTime()));
			ps.setString(6, bean.getMobileNo());
			ps.setString(7, bean.getEmail());
			ps.setString(8, bean.getCreatedBy());
			ps.setString(9, bean.getModifiedBy());
			ps.setTimestamp(10, bean.getCreatedDatetime());
			ps.setTimestamp(11, bean.getModifiedDatetime());
			r = ps.executeUpdate();
		} catch (Exception e) {
			try{
				conn.rollback();
				
			}catch(Exception ex){
				throw new ApplicationException("Exception: delete rollback excption"+ex.getMessage());
				
			}
			throw new ApplicationException("Exception in update student");
			
		} finally {
			JDBCDataSource.closeConn(conn, ps);

		}

		return r;
	}

	public static List<StudentBean> search(StudentBean bean) throws Exception {
		return search(bean, 0, 0);
	}

	/**
	 * search student
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @return list
	 * @throws ApplicationException
	 */
	public static List<StudentBean> search(StudentBean bean, int pageNo, int pageSize) throws ApplicationException {
		ArrayList<StudentBean> list = null;

		StringBuffer sql = new StringBuffer(
				"SELECT ID, COLLEGE_ID,COLLEGE_NAME,FIRST_NAME,LAST_NAME,DATE_OF_BIRTH,MOBILE_NO,EMAIL, CREATED_BY, MODIFIED_BY, CREATED_DATETIME, MODIFIED_DATETIME FROM ST_STUDENT WHERE TRUE");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND ID=" + bean.getId());
			}
			if (bean.getFirstName() != null && bean.getFirstName().length() > 0) {
				sql.append(" AND FIRST_NAME LIKE '" + bean.getFirstName() + "%'");
			}
			if (bean.getLastName() != null && bean.getLastName().length() > 0) {
				sql.append(" AND LAST_NAME LIKE '" + bean.getLastName() + "%'");
			}
			if (bean.getCollegeId() > 0) {
				sql.append(" AND COLLEGE_ID = " + bean.getCollegeId());
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
			list = new ArrayList<StudentBean>();
			while (rs.next()) {
				bean = new StudentBean();
				bean.setId(rs.getLong(1));
				bean.setCollegeId(rs.getLong(2));
				bean.setCollegeName(rs.getString(3));
				bean.setFirstName(rs.getString(4));
				bean.setLastName(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setEmail(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
				list.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			JDBCDataSource.closeConn(conn, ps, rs);

		}
		return list;
	}

	public static ArrayList<StudentBean> list() throws Exception {
		return list(0, 0);
	}

	public static void main(String[] args) throws Exception {
		List<StudentBean> l = list();

		System.out.println(HTMLUtility.getList("st", String.valueOf("1"), l));

	}

	/**
	 * 
	 * list of student
	 * @param pageNo
	 * @param pageSize
	 * @return list
	 * @throws ApplicationException
	 */
	public static ArrayList<StudentBean> list(int pageNo, int pageSize) throws Exception {

		StringBuffer sql = new StringBuffer(
				"SELECT ID,COLLEGE_ID,COLLEGE_NAME,FIRST_NAME,LAST_NAME,DATE_OF_BIRTH,MOBILE_NO,EMAIL, CREATED_BY, MODIFIED_BY, CREATED_DATETIME, MODIFIED_DATETIME FROM ST_STUDENT");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" Limit " + pageNo + ", " + pageSize);
		}

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ArrayList<StudentBean> l = new ArrayList<StudentBean>();
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			StudentBean bean = new StudentBean();
			bean.setId(rs.getLong(1));
			bean.setCollegeId(rs.getLong(2));
			bean.setCollegeName(rs.getString(3));
			bean.setFirstName(rs.getString(4));
			bean.setLastName(rs.getString(5));
			bean.setDob(rs.getDate(6));
			bean.setMobileNo(rs.getString(7));
			bean.setEmail(rs.getString(8));
			bean.setCreatedBy(rs.getString(9));
			bean.setModifiedBy(rs.getString(10));
			bean.setCreatedDatetime(rs.getTimestamp(11));
			bean.setModifiedDatetime(rs.getTimestamp(12));
			l.add(bean);
		}
		JDBCDataSource.closeConn(conn, ps, rs);
		return l;

	}

	/**
	 * find student with the help of emailId
	 * 
	 * @param email
	 * @return bean
	 * @throws ApplicationException
	 */
	public static StudentBean findByEmailId(String emailId) throws ApplicationException {
		Connection conn = null;
		StudentBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM ST_STUDENT WHERE EMAIL=?");
			ps.setString(1, emailId);
			ResultSet rs = ps.executeQuery();
			bean = new StudentBean();
			while (rs.next()) {
				bean.setId(rs.getLong(1));
				bean.setCollegeId(rs.getLong(2));
				bean.setCollegeName(rs.getString(3));
				bean.setFirstName(rs.getString(4));
				bean.setLastName(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setEmail(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
			}
		} catch (Exception e) {
			// log.error("Database Exception.. ", e);
			throw new ApplicationException("Exception: Exception in getting User by Email");

		} finally {
			JDBCDataSource.closeConn(conn);
		}
		return bean;
	}

	/**
	 * find student with the help of pk
	 * 
	 * @param pk
	 * @return bean
	 * @throws ApplicationException
	 */
	public static StudentBean findByPK(long id) throws Exception {
		StringBuffer sql = new StringBuffer("Select * from st_student where id=?");

		StudentBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql.toString());
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			bean = new StudentBean();
			while (rs.next()) {
				bean.setId(rs.getLong(1));
				bean.setCollegeId(rs.getLong(2));
				bean.setCollegeName(rs.getString(3));
				bean.setFirstName(rs.getString(4));
				bean.setLastName(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setEmail(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
			}
		} catch (Exception e) {
			// log.error("Database Exception..",e);
			e.printStackTrace();
			throw new ApplicationException("Exception:Exception in getting User by pk");

		} finally {
			JDBCDataSource.closeConn(conn);
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

		Connection conn = JDBCDataSource.getConnection();

		Statement s = conn.createStatement();

		ResultSet r = s.executeQuery("SELECT MAX(ID) FROM ST_STUDENT");

		while (r.next()) {
			id = r.getLong(1);
		}
		r.close();
		s.close();
		conn.close();

		return id + 1;
	}

}
