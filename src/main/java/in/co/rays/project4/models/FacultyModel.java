package in.co.rays.project4.models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import in.co.rays.project4.beans.CollegeBean;
import in.co.rays.project4.beans.CourseBean;
import in.co.rays.project4.beans.FacultyBean;
import in.co.rays.project4.beans.SubjectBean;
import in.co.rays.project4.exception.ApplicationException;
import in.co.rays.project4.exception.DuplicateException;
import in.co.rays.project4.util.JDBCDataSource;

/**
 * JDBC Implements of faculty model
 * 
 * @author Gagan
 *
 */
public class FacultyModel {

	public static void main(String[] args) throws Exception {
		FacultyBean b = new FacultyBean();
		b.setFirstName("priyallll");
		b.setLastName("kar");
		b.setId(1);
		b.setDoj(new Timestamp(new java.util.Date().getTime()));
		b.setCollegeId(1);
		b.setCourseId(1);
		b.setSubId(1);
		System.out.println(update(b));

	}

	/**
	 * add new faculty
	 * 
	 * @param fbean
	 * @return pk
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public static long add(FacultyBean fb) throws ApplicationException, DuplicateException {
		Connection conn = null;

		int pk = 0;

		CollegeBean collegeBean = CollegeModel.findByPK(fb.getCollegeId());
		fb.setCollegeName(collegeBean.getName());

		CourseBean courseBean = CourseModel.findByPK(fb.getCourseId());
		fb.setCourseName(courseBean.getCourseName());

		SubjectBean subjectBean;
		try {
			subjectBean = SubModel.findByPk(fb.getSubId());
			fb.setSubName(subjectBean.getSubName());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		FacultyBean duplicataRole = findByName(fb.getFirstName());
		// Check if create Faculty already exist
		if (duplicataRole != null) {
			throw new DuplicateException("Faculty already exists");
		}

		System.out.println("Date   " + new Date(fb.getDoj().getTime()));

		PreparedStatement ps = null;
		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			ps = conn.prepareStatement("INSERT INTO ST_FACULTY VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pk = nextPK();
			ps.setLong(1, pk);
			ps.setString(4, fb.getFirstName());
			ps.setString(5, fb.getLastName());
			ps.setString(6, fb.getGender());
			ps.setDate(7, new Date(fb.getDoj().getTime()));
			ps.setString(8, fb.getQualification());
			ps.setString(9, fb.getEmail());
			ps.setString(10, fb.getMobileNo());
			ps.setLong(2, fb.getCollegeId());
			ps.setString(3, fb.getCollegeName());
			ps.setLong(11, fb.getCourseId());
			ps.setString(12, fb.getCourseName());
			ps.setLong(13, fb.getSubId());
			ps.setString(14, fb.getSubName());
			ps.setString(15, fb.getCreatedBy());
			ps.setString(16, fb.getModifiedBy());
			ps.setTimestamp(17, fb.getCreatedDatetime());
			ps.setTimestamp(18, fb.getModifiedDatetime());

			ps.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("Database Exception..", e);
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in add Faculty");
		} finally {
			JDBCDataSource.closeConn(conn, ps);
		}
		System.out.println("add faculty");
		return pk;
	}

	/**
	 * find faculty by name
	 * 
	 * @param firstName
	 * @return bean
	 * @throws ApplicationException
	 */
	private static FacultyBean findByName(String firstName) throws ApplicationException {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer("Select * from st_faculty where first_name=?");

		FacultyBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql.toString());
			stmt.setString(1, firstName);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				bean = new FacultyBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(4));
				bean.setLastName(rs.getString(5));
				bean.setGender(rs.getString(6));
				bean.setDoj(rs.getTimestamp(7));
				bean.setQualification(rs.getString(8));
				bean.setEmail(rs.getString(9));
				bean.setMobileNo(rs.getString(10));
				bean.setCollegeId(rs.getInt(2));
				bean.setCollegeName(rs.getString(3));
				bean.setCourseId(rs.getInt(11));
				bean.setCourseName(rs.getString(12));
				bean.setSubId(rs.getInt(13));
				bean.setSubName(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDatetime(rs.getTimestamp(17));
				bean.setModifiedDatetime(rs.getTimestamp(18));

			}
			rs.close();

		} catch (Exception e) {
			// log.error("Database Exception..",e);
			throw new ApplicationException("Exception:Exception in getting Faculty by Name");

		} finally {
			JDBCDataSource.closeConn(conn);
		}
		// log.debug("Model findByName Ended");
		return bean;
	}

	/**
	 * delete faculty
	 * 
	 * @param bean
	 * @throws ApplicationException
	 */
	public static int delete(FacultyBean bean) throws ApplicationException {

		int r = 0;

		Connection conn = null;
		PreparedStatement ps = null;
		try {

			conn = JDBCDataSource.getConnection();
			ps = conn.prepareStatement("DELETE FROM ST_FACULTY WHERE ID = ?");
			ps.setLong(1, bean.getId());
			r = ps.executeUpdate();
		} catch (Exception e) {
			// log.error("DATABASE EXCEPTION ", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception in Faculty Model rollback" + ex.getMessage());
			}
			throw new ApplicationException("Exception in Faculty Model Delete Method");
		} finally {
			JDBCDataSource.closeConn(conn, ps);
		}
		return r;
	}

	/**
	 * update faculty information
	 * 
	 * @param bean
	 * @throws ApplicationException
	 * @throws DuplicateException 
	 * @throws DatabaseException
	 */
	public static String update(FacultyBean fb) throws ApplicationException, DuplicateException {
		String r = null;

		Connection conn = null;
		PreparedStatement ps = null;

		CollegeBean collegeBean = CollegeModel.findByPK(fb.getCollegeId());
		fb.setCollegeName(collegeBean.getName());

		CourseBean courseBean = CourseModel.findByPK(fb.getCourseId());
		fb.setCourseName(courseBean.getCourseName());

//		FacultyBean beanexist = findByName(fb.getFirstName());
//		if (beanexist != null && beanexist.getId() != fb.getId()) {
//			throw new DuplicateException("Faculty is already exist");
//		}

		SubjectBean subjectBean;
		try {
			subjectBean = SubModel.findByPk(fb.getSubId());
			fb.setSubName(subjectBean.getSubName());

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {

			conn = JDBCDataSource.getConnection();
			
			conn.setAutoCommit(false);
			
			System.out.println("update faculty");

			ps = conn.prepareStatement(
					"UPDATE ST_FACULTY SET FIRST_NAME=?, LAST_NAME=?, GENDER=?, DATE_OF_JOURANY=?, QUALIFICATION=?, EMAIL=?, MOBILE_NO=? , COLLEGE_ID=? , COLLEGE_NAME=?,COURSE_ID=?,COURSE_NAME=?, SUB_ID=?, SUB_NAME=?, CREATED_BY=? , MODIFIED_BY=? , CREATED_DATETIME=? , MODIFIED_DATETIME=? WHERE ID=? ");
			ps.setString(1, fb.getFirstName());
			ps.setString(2, fb.getLastName());
			ps.setString(3, fb.getGender());
			ps.setDate(4, new Date(fb.getDoj().getTime()));
			ps.setString(5, fb.getQualification());
			ps.setString(6, fb.getEmail());
			ps.setString(7, fb.getMobileNo());
			ps.setLong(8, fb.getCollegeId());
			ps.setString(9, fb.getCollegeName());
			ps.setLong(10, fb.getCourseId());
			ps.setString(11, fb.getCourseName());
			ps.setLong(12, fb.getSubId());
			ps.setString(13, fb.getSubName());
			ps.setString(14, fb.getCreatedBy());
			ps.setString(15, fb.getModifiedBy());
			ps.setTimestamp(16, fb.getCreatedDatetime());
			ps.setTimestamp(17, fb.getModifiedDatetime());
			ps.setLong(18, fb.getId());
			
			ps.executeUpdate();
			System.out.println("update endd");
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception in rollback faculty model .." + ex.getMessage());
			}
			throw new ApplicationException("Exception in faculty model Update Method..");
		} finally {

			JDBCDataSource.closeConn(conn, ps);
		}
		return r;
	}

	public static List<FacultyBean> search(FacultyBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	/**
	 * to search list of faculty
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @return list
	 * @throws ApplicationException
	 */  
	public static List<FacultyBean> search(FacultyBean bean, int pageNo, int pageSize) throws ApplicationException {
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY WHERE true");
		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			if (bean.getCollegeId() > 0) {
				sql.append(" AND college_Id = " + bean.getCollegeId());
			}
			if (bean.getFirstName() != null && bean.getFirstName().trim().length() > 0) {
				sql.append(" AND FIRST_NAME like '" + bean.getFirstName() + "%'");
			}
			if (bean.getLastName() != null && bean.getLastName().trim().length() > 0) {
				sql.append(" AND LAST_NAME like '" + bean.getLastName() + "%'");
			}
			if (bean.getCollegeName() != null && bean.getCollegeName().length() > 0) {
				sql.append(" AND college_Name like '" + bean.getCollegeName() + "%'");
			}
			if (bean.getCourseId() > 0) {
				sql.append(" AND course_Id = " + bean.getCourseId());
			}
			if (bean.getCourseName() != null && bean.getCourseName().length() > 0) {
				sql.append(" AND course_Name like '" + bean.getCourseName() + "%'");
			}
			if (bean.getSubId() > 0) {
				sql.append(" AND Subject_Id = " + bean.getSubId());
			}
			if (bean.getSubName() != null && bean.getSubName().length() > 0) {
				sql.append(" AND subject_Name like '" + bean.getSubName() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" Limit " + pageNo + ", " + pageSize);
		}

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<FacultyBean> list = null;
		try {

			conn = JDBCDataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());

			rs = ps.executeQuery();
			list = new ArrayList<FacultyBean>();
			while (rs.next()) {
				bean = new FacultyBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(4));
				bean.setLastName(rs.getString(5));
				bean.setGender(rs.getString(6));
				bean.setDoj(rs.getDate(7));
				bean.setQualification(rs.getString(8));
				bean.setEmail(rs.getString(9));
				bean.setMobileNo(rs.getString(10));
				bean.setCollegeId(rs.getInt(2));
				bean.setCollegeName(rs.getString(3));
				bean.setCourseId(rs.getInt(11));
				bean.setCourseName(rs.getString(12));
				bean.setSubId(rs.getInt(13));
				bean.setSubName(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDatetime(rs.getTimestamp(17));
				bean.setModifiedDatetime(rs.getTimestamp(18));
				list.add(bean);
			}
		} catch (Exception e) {
//			e.printStackTrace();
			// log.error("database Exception .. " , e);
//			e.printStackTrace();
			 throw new ApplicationException("Exception : Exception in Search method of Faculty Model");
		} finally {

			JDBCDataSource.closeConn(conn, ps, rs);
		}
		return list;
	}

	/**
	 * find information with the help of pk
	 * @param pk
	 * @return bean
	 * @throws ApplicationException
	 */
	public static FacultyBean findByPK(long pk) throws Exception {

		FacultyBean fb = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<CourseBean> l = null;
		try {
			conn = JDBCDataSource.getConnection();
			ps = conn.prepareStatement("SELECT * FROM ST_FACULTY WHERE ID=?");
			ps.setLong(1, pk);
			rs = ps.executeQuery();
			fb = new FacultyBean();
			while (rs.next()) {
				fb.setId(rs.getInt(1));
				fb.setFirstName(rs.getString(4));
				fb.setLastName(rs.getString(5));
				fb.setGender(rs.getString(6));
				fb.setDoj(rs.getTimestamp(7));
				fb.setQualification(rs.getString(8));
				fb.setEmail(rs.getString(9));
				fb.setMobileNo(rs.getString(10));
				fb.setCollegeId(rs.getInt(2));
				fb.setCollegeName(rs.getString(3));
				fb.setCourseId(rs.getInt(11));
				fb.setCourseName(rs.getString(12));
				fb.setSubId(rs.getInt(13));
				fb.setSubName(rs.getString(14));
				fb.setCreatedBy(rs.getString(15));
				fb.setModifiedBy(rs.getString(16));
				fb.setCreatedDatetime(rs.getTimestamp(17));
				fb.setModifiedDatetime(rs.getTimestamp(18));

			}
		} finally {
			JDBCDataSource.closeConn(conn, ps, rs);
		}
		return fb;
	}

	public static List<FacultyBean> list() throws ApplicationException {
		return list(0, 0);
	}

	 /**
	 * to show list of faculty
	 * @param pageNo
	 * @param pageSize
	 * @return list
	 * @throws ApplicationException
	 */
	public static List<FacultyBean> list(int pageNo, int pageSize) throws ApplicationException {
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + " , " + pageSize);
		}

		Connection conn = null;
		ArrayList<FacultyBean> list = new ArrayList<FacultyBean>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			conn = JDBCDataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				FacultyBean fb = new FacultyBean();
				fb.setId(rs.getInt(1));
				fb.setFirstName(rs.getString(4));
				fb.setLastName(rs.getString(5));
				fb.setGender(rs.getString(6));
				fb.setDoj(rs.getTimestamp(7));
				fb.setQualification(rs.getString(8));
				fb.setEmail(rs.getString(9));
				fb.setMobileNo(rs.getString(10));
				fb.setCollegeId(rs.getInt(2));
				fb.setCollegeName(rs.getString(3));
				fb.setCourseId(rs.getInt(11));
				fb.setCourseName(rs.getString(12));
				fb.setSubId(rs.getInt(13));
				fb.setSubName(rs.getString(14));
				fb.setCreatedBy(rs.getString(15));
				fb.setModifiedBy(rs.getString(16));
				fb.setCreatedDatetime(rs.getTimestamp(17));
				fb.setModifiedDatetime(rs.getTimestamp(18));

				list.add(fb);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("Database Exception ......" , e);
			throw new ApplicationException("Exception in list method of FacultyModel");
		} finally {
			JDBCDataSource.closeConn(conn, ps, rs);
		}
		return list;
	}

	/**
	 * new id create
	 * @return pk
	 * @throws DatabaseException
	 */
	public static Integer nextPK() throws Exception {
		int pk = 0;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JDBCDataSource.getConnection();
			ps = conn.prepareStatement("SELECT MAX(ID) FROM ST_FACULTY");
			rs = ps.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
		} finally {
			JDBCDataSource.closeConn(conn, ps, rs);
		}
		return pk + 1;
	}
}
