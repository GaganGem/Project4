package in.co.rays.project4.models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.project4.beans.CourseBean;
import in.co.rays.project4.beans.SubjectBean;
import in.co.rays.project4.beans.TimetableBean;
import in.co.rays.project4.exception.ApplicationException;
import in.co.rays.project4.exception.DuplicateException;
import in.co.rays.project4.util.JDBCDataSource;

/**
 * JDBC Implements of timetable
 * @author  Gagan
 *
 */
public class TimetableModel {

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
		PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(id) FROM ST_TIMETABLE");
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			pk = rs.getInt(1);
		}
		rs.close();

		JDBCDataSource.closeConn(conn, pstmt, rs);
		return pk + 1;
	}

	/**
	 * add timetable
	 * 
	 * @param bean
	 * @return pk
	 * @throws ApplicationException
	 */
	public static int add(TimetableBean bean) throws ApplicationException, DuplicateException {
		// log.debug("TimeTable model Add method End");
		Connection conn = null;
		int pk = 0;

		CourseBean coubean = CourseModel.findByPK(bean.getCourseId());
		String CourseName = coubean.getCourseName();

		SubjectBean sbean;
		String SubjcetName = null;
		try {
			sbean = SubModel.findByPk(bean.getSubId());
			SubjcetName = sbean.getSubName();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		TimetableBean bean1 = checkBycds(bean.getCourseId(), bean.getSem(),
				new java.sql.Date(bean.getExamDate().getTime()));
		TimetableBean bean2 = checkBycss(bean.getCourseId(), bean.getSubId(), bean.getSem());
		if (bean1 != null || bean2 != null) {
			 throw new DuplicateException("TimeTable Already Exsist");

		}

		try {
			System.out.println(bean.getExamDate());
			System.out.println(bean.getSem());

			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ST_TIMETABLE VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setString(3, CourseName);
			pstmt.setLong(2, bean.getCourseId());
			pstmt.setString(5, SubjcetName);
			pstmt.setLong(4, bean.getSubId());
			pstmt.setDate(6, new java.sql.Date(bean.getExamDate().getTime()));
			pstmt.setString(7, bean.getExamTime());
			pstmt.setString(8, bean.getSem());
			pstmt.setString(9, bean.getCreatedBy());
			pstmt.setString(10, bean.getModifiedBy());
			pstmt.setTimestamp(11, bean.getCreatedDatetime());
			pstmt.setTimestamp(12, bean.getModifiedDatetime());
			pstmt.executeUpdate();

			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("database Exception ...", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception in the Rollback of TIMETABLE Model" + ex.getMessage());
			}
			throw new ApplicationException("Exception in Add method of TIMETABLE Model");
		} finally {
			JDBCDataSource.closeConn(conn);
		}
		// log.debug("TimeTable model Add method End");
		return pk;

	}

	/**
	 * delete timetable
	 * 
	 * @param bean
	 * @throws ApplicationException
	 */
	public static void delete(TimetableBean bean) throws Exception {
		Connection conn = null;
		conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_TIMETABLE WHERE ID=?");
		pstmt.setLong(1, bean.getId());
		pstmt.executeUpdate();
		JDBCDataSource.closeConn(conn);
	}

	/**
	 * update timetable
	 * 
	 * @param bean
	 * @throws ApplicationException
	 * @throws DatabaseException
	 */
	public static void update(TimetableBean bean) throws ApplicationException, DuplicateException {
		//	log.debug("TIMETABLE Model update method Started");
			Connection conn = null;
//			CourseModel coumodel= new CourseModel();
//			CourseBean coubean=    coumodel.findByPk(bean.getCourse_Id());
//			String courseName= coubean.getCourse_Name();
//			
//			SubjectModel  smodel = new SubjectModel();
//			SubjectBean sbean = smodel.findByPk(bean.getSubject_Id());
//			String subjectName= sbean.getSubject_Name();
	//	

			CourseBean coubean=    CourseModel.findByPK(bean.getCourseId());
			String CourseName= coubean.getCourseName();
			
			SubjectBean sbean;
			String SubjcetName= null;
			try {
				sbean = SubModel.findByPk(bean.getSubId());
				SubjcetName= sbean.getSubName();
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			TimetableBean bean1 = checkBycds(bean.getCourseId(), bean.getSem(),  new java.sql.Date(bean.getExamDate().getTime()));
			TimetableBean bean2 = checkBycss(bean.getCourseId(), bean.getSubId(), bean.getSem());
			 if(bean1 != null || bean2 != null){ 
				 throw new DuplicateException("TimeTable Already Exsist"); 
				 
			 }
			
			try {
				conn = JDBCDataSource.getConnection();
				conn.setAutoCommit(false);
				PreparedStatement pstmt = conn.prepareStatement(
						"UPDATE ST_TIMETABLE SET COURSE_NAME=?,COURSE_ID=?,SUBJECT_NAME=?,SUBJECT_ID=?,EXAM_DATE=?,EXAM_TIME=?,SEMESTER=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
				
				pstmt.setString(1, CourseName);
				pstmt.setLong(2, bean.getCourseId());
				pstmt.setString(3, SubjcetName);
				pstmt.setLong(4, bean.getSubId());
			    pstmt.setDate(5, new java.sql.Date(bean.getExamDate().getTime()));
				pstmt.setString(6, bean.getExamTime());
				pstmt.setString(7, bean.getSem());
				pstmt.setString(8, bean.getCreatedBy());
				pstmt.setString(9, bean.getModifiedBy());
				pstmt.setTimestamp(10, bean.getCreatedDatetime());
				pstmt.setTimestamp(11, bean.getModifiedDatetime());
				pstmt.setLong(12, bean.getId());
				
			pstmt.executeUpdate();

				conn.commit();
				pstmt.close();

			} catch (Exception e) {
				e.printStackTrace();
				//log.error("database Exception....", e);
				try {
					conn.rollback();
				} catch (Exception ex) {
					throw new ApplicationException(
							"Exception in Rollback of Update Method of TimeTable Model" + ex.getMessage());
				}
				throw new ApplicationException("Exception in update Method of TimeTable Model");
			} finally {
				JDBCDataSource.closeConn(conn);
			}
			//log.debug("TimeTable Model Update method End");
		}
	public TimetableBean findByName(String name) throws Exception {
		Connection conn = null;
		TimetableBean bean = null;
		conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM ST_TIMETABLE WHERE SubjectName = ?");
		pstmt.setString(1, name);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			bean = new TimetableBean();

			bean.setId(rs.getLong(1));
			bean.setCourseId(rs.getLong(2));
			bean.setCourseName(rs.getString(3));
			bean.setSubId(rs.getLong(4));
			bean.setSubName(rs.getString(5));
			bean.setExamDate(rs.getDate(6));
			bean.setExamTime(rs.getString(7));
			bean.setSem(rs.getString(8));
			bean.setCreatedBy(rs.getString(9));
			bean.setModifiedBy(rs.getString(10));
			bean.setCreatedDatetime(rs.getTimestamp(11));
			bean.setModifiedDatetime(rs.getTimestamp(12));
		}
		rs.close();
		JDBCDataSource.closeConn(conn);

		return bean;
	}

	/**
	 * find time table by pk
	 * 
	 * @param pk
	 * @return bean
	 * @throws ApplicationException
	 */
	public static TimetableBean findByPk(long pk) throws Exception {
		Connection conn = null;
		TimetableBean bean = null;
		conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM ST_TIMETABLE WHERE ID=?");
		pstmt.setLong(1, pk);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			bean = new TimetableBean();

			bean.setId(rs.getLong(1));
			bean.setSubId(rs.getLong(4));
			bean.setSubName(rs.getString(5));
			bean.setCourseId(rs.getLong(2));
			bean.setCourseName(rs.getString(3));
			bean.setExamDate(rs.getDate(6));
			bean.setExamTime(rs.getString(7));
			bean.setSem(rs.getString(8));
			bean.setCreatedBy(rs.getString(9));
			bean.setModifiedBy(rs.getString(10));
			bean.setCreatedDatetime(rs.getTimestamp(11));
			bean.setModifiedDatetime(rs.getTimestamp(12));
		}
		rs.close();
		JDBCDataSource.closeConn(conn);

		return bean;
	}

	public static ArrayList<TimetableBean> search(TimetableBean bean) throws Exception {
		return search(bean, 0, 0);
	}

	/**
	 * search time table
	 * 
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws ApplicationException
	 */
	public static ArrayList<TimetableBean> search(TimetableBean bean, int pageNo, int pageSize) throws Exception {

		Connection conn = null;
		ArrayList<TimetableBean> list = new ArrayList<TimetableBean>();
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			if (bean.getCourseId() > 0) {
				sql.append(" AND Course_ID = " + bean.getCourseId());
			}
			if (bean.getSubId() > 0) {
				sql.append(" AND Sub_ID = " + bean.getSubId());
			}

		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" Limit " + pageNo + ", " + pageSize);
		}
		conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			bean = new TimetableBean();
			bean.setId(rs.getLong(1));
			bean.setSubId(rs.getLong(4));
			bean.setSubName(rs.getString(5));
			bean.setCourseId(rs.getLong(2));
			bean.setCourseName(rs.getString(3));
			bean.setExamDate(rs.getDate(6));
			bean.setExamTime(rs.getString(7));
			bean.setSem(rs.getString(8));
			bean.setCreatedBy(rs.getString(9));
			bean.setModifiedBy(rs.getString(10));
			bean.setCreatedDatetime(rs.getTimestamp(11));
			bean.setModifiedDatetime(rs.getTimestamp(12));
			list.add(bean);
		}
		JDBCDataSource.closeConn(conn, pstmt, rs);
		return list;
	}

	public static List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 * list of time table
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws ApplicationException
	 */
	public static List list(int pageNo, int pageSize) throws ApplicationException {
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE ");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + " , " + pageSize);
		}

		Connection conn = null;
		ArrayList<TimetableBean> list = new ArrayList<TimetableBean>();
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {

				TimetableBean bean = new TimetableBean();

				bean.setId(rs.getLong(1));
				bean.setSubId(rs.getLong(4));
				bean.setSubName(rs.getString(5));
				bean.setCourseId(rs.getLong(2));
				bean.setCourseName(rs.getString(3));
				bean.setExamDate(rs.getDate(6));
				bean.setExamTime(rs.getString(7));
				bean.setSem(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("database Exception....", e);
			throw new ApplicationException("Exception in list Method of timetable Model");
		} finally {
			JDBCDataSource.closeConn(conn);
		}
		// log.debug("Timetable Model list method End");
		return list;
	}

	/**
	 * Check bycss.
	 *
	 * @param CourseId
	 *            the course id
	 * @param SubjectId
	 *            the subject id
	 * @param i
	 *            the semester
	 * @return the time table bean
	 * @throws ApplicationException
	 *             the application exception
	 */
	public static TimetableBean checkBycss(long CourseId, long SubjectId, String i) throws ApplicationException {
		System.out.println("in from css.........................<<<<<<<<<<<>>>> ");
		Connection conn = null;
		TimetableBean bean = null;
		// java.util.Date ExamDAte,String ExamTime
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE Course_ID=? AND Sub_ID=? AND Sem=?");

		try {
			Connection con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement(sql.toString());
			ps.setLong(1, CourseId);
			ps.setLong(2, SubjectId);
			ps.setString(3, i);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new TimetableBean();
				bean.setId(rs.getLong(1));
				bean.setSubId(rs.getLong(4));
				bean.setSubName(rs.getString(5));
				bean.setCourseId(rs.getLong(2));
				bean.setCourseName(rs.getString(3));
				bean.setExamDate(rs.getDate(6));
				bean.setExamTime(rs.getString(7));
				bean.setSem(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("database Exception....", e);
			throw new ApplicationException("Exception in list Method of timetable Model");
		} finally {
			JDBCDataSource.closeConn(conn);
		}
		// log.debug("Timetable Model list method End");
		System.out.println("out from css.........................<<<<<<<<<<<>>>> ");
		return bean;
	}

	/**
	 * Check bycds.
	 *
	 * @param CourseId
	 *            the course id
	 * @param i
	 *            the semester
	 * @param ExamDate
	 *            the exam date
	 * @return the time table bean
	 * @throws ApplicationException
	 *             the application exception
	 */
	public static TimetableBean checkBycds(long CourseId, String i, Date ExamDate) throws ApplicationException {
		System.out.println("in from cds.........................<<<<<<<<<<<>>>> ");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE Course_ID=? AND Sem=? AND Exam_Date=?");

		Connection conn = null;
		TimetableBean bean = null;
		// Date ExDate = new Date(ExamDAte.getTime());

		try {
			Connection con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement(sql.toString());
			ps.setLong(1, CourseId);
			ps.setString(2, i);
			ps.setDate(3, (java.sql.Date) ExamDate);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new TimetableBean();
				bean.setId(rs.getLong(1));
				bean.setSubId(rs.getLong(4));
				bean.setSubName(rs.getString(5));
				bean.setCourseId(rs.getLong(2));
				bean.setCourseName(rs.getString(3));
				bean.setExamDate(rs.getDate(6));
				bean.setExamTime(rs.getString(7));
				bean.setSem(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("database Exception....", e);
			throw new ApplicationException("Exception in list Method of timetable Model");
		} finally {
			JDBCDataSource.closeConn(conn);
		}
		// log.debug("Timetable Model list method End");
		System.out.println("out from cds.........................<<<<<<<<<<<>>>> ");
		return bean;
	}

	/**
	 * Check bysemester.
	 *
	 * @param CourseId
	 *            the course id
	 * @param SubjectId
	 *            the subject id
	 * @param semester
	 *            the semester
	 * @param ExamDAte
	 *            the exam D ate
	 * @return the time table bean
	 */
	public static TimetableBean checkBysemester(long CourseId, long SubjectId, String semester,
			java.util.Date ExamDAte) {

		TimetableBean bean = null;

		Date ExDate = new Date(ExamDAte.getTime());

		StringBuffer sql = new StringBuffer(
				"SELECT * FROM TIMETABLE WHERE COURSE_ID=? AND SUBJECT_ID=? AND" + " SEMESTER=? AND EXAM_DATE=?");

		try {
			Connection con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement(sql.toString());
			ps.setLong(1, CourseId);
			ps.setLong(2, SubjectId);
			ps.setString(3, semester);
			ps.setDate(4, ExDate);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new TimetableBean();
				bean.setId(rs.getLong(1));
				bean.setSubId(rs.getLong(4));
				bean.setSubName(rs.getString(5));
				bean.setCourseId(rs.getLong(2));
				bean.setCourseName(rs.getString(3));
				bean.setExamDate(rs.getDate(6));
				bean.setExamTime(rs.getString(7));
				bean.setSem(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}

	/**
	 * Check by course name.
	 *
	 * @param CourseId
	 *            the course id
	 * @param ExamDate
	 *            the exam date
	 * @return the time table bean
	 */
	public static TimetableBean checkByCourseName(long CourseId, java.util.Date ExamDate) {
		TimetableBean bean = null;

		Date Exdate = new Date(ExamDate.getTime());

		StringBuffer sql = new StringBuffer("SELECT * FROM TIMETABLE WHERE COURSE_ID=? " + "AND EXAM_DATE=?");

		try {
			Connection con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement(sql.toString());
			ps.setLong(1, CourseId);
			ps.setDate(2, Exdate);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new TimetableBean();
				bean.setId(rs.getLong(1));
				bean.setSubId(rs.getLong(4));
				bean.setSubName(rs.getString(5));
				bean.setCourseId(rs.getLong(2));
				bean.setCourseName(rs.getString(3));
				bean.setExamDate(rs.getDate(6));
				bean.setExamTime(rs.getString(7));
				bean.setSem(rs.getString(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}

}
