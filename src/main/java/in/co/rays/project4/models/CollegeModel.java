package in.co.rays.project4.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import in.co.rays.project4.beans.CollegeBean;
import in.co.rays.project4.exception.ApplicationException;
import in.co.rays.project4.exception.DataBaseException;
import in.co.rays.project4.exception.DuplicateException;
import in.co.rays.project4.util.JDBCDataSource;

/**
* JDBC implements of college model
* @author Gagan
*
*/
public class CollegeModel {

	
	/**
	* add new college
	* @param bean
	* @return
	* @throws ApplicationException
	* @throws DuplicateRecordException
	*/
	public static int add(CollegeBean bean) throws ApplicationException, DuplicateException {
		int result = 0;
		Connection conn = null;
		PreparedStatement ps = null;

		CollegeBean duplicateCollegeName = findByName(bean.getName());

		if (duplicateCollegeName != null) {
			throw new DuplicateException("College Name already exists");
		}
		try {
			conn = JDBCDataSource.getConnection();
			ps = conn.prepareStatement("INSERT INTO ST_COLLEGE VALUES(?,?,?,?,?,?,?,?,?,?)");

			ps.setLong(1, nextPk());
			ps.setString(2, bean.getName());
			ps.setString(3, bean.getAddress());
			ps.setString(4, bean.getState());
			ps.setString(5, bean.getCity());
			ps.setString(6, bean.getPhoneNo());
			ps.setString(7, bean.getCreatedBy());
			ps.setString(8, bean.getModifiedBy());
			ps.setTimestamp(9, bean.getCreatedDatetime());
			ps.setTimestamp(10, bean.getModifiedDatetime());
			result = ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			// log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add College");
		} finally {
			JDBCDataSource.closeConn(conn, ps);
		}
		return result;
	}

	/**
	* delete college information
	* @param b
	* @throws DatabaseException
	*/
	public static int delete(CollegeBean bean) throws DuplicateException, ApplicationException {

		int r = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = JDBCDataSource.getConnection();
			ps = conn.prepareStatement("DELETE FROM ST_COLLEGE WHERE ID = ?");
			ps.setLong(1, bean.getId());
			r = ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			// log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete college");
		} finally {
			JDBCDataSource.closeConn(conn, ps);
		}
		return r;
	}

	/**
	* update college detail
	* @param bean
	* @throws ApplicationException
	* @throws DuplicateRecordException
	*/
	public static int update(CollegeBean bean) throws ApplicationException, DuplicateException {
		int r = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		conn = JDBCDataSource.getConnection();

		CollegeBean beanExist = findByName(bean.getName());

		if (beanExist != null && beanExist.getId() != bean.getId()) {

			throw new DuplicateException("College is already exist");
		}
		try {
			ps = conn.prepareStatement(
					"UPDATE ST_COLLEGE SET NAME=?,ADDRESS=?,STATE=?,CITY=?,PHONE_NO=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID = ?");
			ps.setLong(10, bean.getId());
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getAddress());
			ps.setString(3, bean.getState());
			ps.setString(4, bean.getCity());
			ps.setString(5, bean.getPhoneNo());
			ps.setString(6, bean.getCreatedBy());
			ps.setString(7, bean.getModifiedBy());
			ps.setTimestamp(8, bean.getCreatedDatetime());
			ps.setTimestamp(9, bean.getModifiedDatetime());

			r = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			 throw new ApplicationException("Exception in updating College ");
		} finally {
			JDBCDataSource.closeConn(conn, ps);
		}
		return r;
	}

	/**
	* search college information
	* @param cbean1
	* @param pageNo
	* @param pageSize
	* @return list
	* @throws ApplicationException
	*/
	public static List<CollegeBean> search(CollegeBean bean, int pageNo, int pageSize) throws ApplicationException {
		ArrayList<CollegeBean> list = null;

		StringBuffer sql = new StringBuffer(
				"SELECT NAME,ADDRESS,STATE,CITY,PHONE_NO,CREATED_BY,MODIFIED_BY,CREATED_DATETIME,MODIFIED_DATETIME,ID FROM ST_COLLEGE WHERE TRUE");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND ID=" + bean.getId());
			}
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND NAME LIKE '" + bean.getName() + "%'");
			}
			if (bean.getCity() != null && bean.getCity().length() > 0) {
				sql.append(" AND CITY LIKE '" + bean.getCity() + "%'");
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
			list = new ArrayList<CollegeBean>();
			while (rs.next()) {
				bean = new CollegeBean();
				bean.setName(rs.getString(1));
				bean.setAddress(rs.getString(2));
				bean.setState(rs.getString(3));
				bean.setCity(rs.getString(4));
				bean.setPhoneNo(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
				bean.setId(rs.getLong(10));
				list.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in search college");
		} finally {
			JDBCDataSource.closeConn(conn, ps, rs);
		}
		return list;
	}

	public static List<CollegeBean> search(CollegeBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	public static List<CollegeBean> list() throws Exception {
		return list(0, 0);
	}

	/**
	* to show list of college
	* @param pageNo
	* @param pageSize
	* @return list
	* @throws ApplicationException
	*/
	public static ArrayList<CollegeBean> list(int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer(
				"SELECT ID,NAME,ADDRESS,STATE,CITY,PHONE_NO,CREATED_BY,MODIFIED_BY,CREATED_DATETIME,MODIFIED_DATETIME FROM ST_COLLEGE");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" Limit " + pageNo + ", " + pageSize);
		}

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<CollegeBean> l = null;
		try {
			conn = JDBCDataSource.getConnection();

			ps = conn.prepareStatement(sql.toString());
			l = new ArrayList<CollegeBean>();
			rs = ps.executeQuery();
			while (rs.next()) {
				CollegeBean bean = new CollegeBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
				bean.setPhoneNo(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
				l.add(bean);
			}
		} catch (Exception e) {
			// log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting list of users");
		} finally {
			JDBCDataSource.closeConn(conn, ps, rs);
		}
		return l;
	}

	/**
	* find the infromation with the help of college name
	* @param name
	* @return bean
	* @throws ApplicationException
	*/
	public static CollegeBean findByName(String name) throws ApplicationException {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		CollegeBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			ps = conn.prepareStatement("SELECT * FROM ST_COLLEGE WHERE NAME=?");
			ps.setString(1, name);
			rs = ps.executeQuery();
			while (rs.next()) {
				bean = new CollegeBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
				bean.setPhoneNo(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
			}
		} catch (Exception e) {
			// log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting College by Name");
		} finally {
			JDBCDataSource.closeConn(conn, ps, rs);
		}
		return bean;
	}

	/**
	* find the information with the help of pk
	* @param pk
	* @return bean
	* @throws ApplicationException
	*/
	public static CollegeBean findByPK(long pk) throws ApplicationException {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		CollegeBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			ps = conn.prepareStatement("SELECT * FROM ST_COLLEGE WHERE ID=?");
			ps.setLong(1, pk);
			rs = ps.executeQuery();
			bean = new CollegeBean();
			while (rs.next()) {
				bean = new CollegeBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
				bean.setPhoneNo(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));

			}
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting College by pk");
		} finally {
			JDBCDataSource.closeConn(conn, ps, rs);
		}
		return bean;
	}

	/**
	* find pk
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

			r = s.executeQuery("SELECT MAX(ID) FROM ST_COLLEGE");

			while (r.next()) {
				id = r.getLong(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("Database Exception..", e);
			throw new DataBaseException("Exception : Exception in getting PK");
		} finally {
			r.close();
			s.close();
			conn.close();
		}

		return id + 1;
	}

	// public static void main(String[] args) throws Exception {
	// CollegeBean b = new CollegeBean();
	// String d = "22/7/2021";
	// SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	// Date date = dateFormat.parse(d);
	// Timestamp timestamp = new Timestamp(date.getTime());

	// b.setId(1);
	// b.setName("ACROPOLIS");
	// b.setAddress("DEWAS bypass");
	// b.setCity("indore");
	// b.setState("MP");
	// b.setPhoneNo("07314545444");
	// b.setCreatedBy("gagan");
	// b.setModifiedBy("gagan");
	// b.setCreatedDatetime(timestamp);

	// System.out.println(CollegeModel.add(b));
	// System.out.println(CollegeModel.update(b));
	// System.out.println(CollegeModel.delete(b));
	// System.out.println(b.getAddress());
	// System.out.println(CollegeModel.search(b));
	// System.out.println(b.getAddress());

	// ArrayList<CollegeBean> list = CollegeModel.list();
	// for (CollegeBean c : list) {
	// System.out.println(c.getName());
	//
	// }

	// }
}
