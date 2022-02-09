
package in.co.rays.project4.models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import in.co.rays.project4.beans.UserBean;
import in.co.rays.project4.exception.ApplicationException;
import in.co.rays.project4.exception.DataBaseException;
import in.co.rays.project4.exception.DuplicateException;
import in.co.rays.project4.util.EmailBuilder;
import in.co.rays.project4.util.EmailMessage;
import in.co.rays.project4.util.EmailUtility;
import in.co.rays.project4.util.JDBCDataSource;

/**
 * JDBC Implement of user model
 * 
 * @author Gagan
 *
 */
public class UserModel {

	/**
	 * add user
	 * 
	 * @param bean
	 * @return pk
	 * @throws ApplicationException
	 * @throws DuplicateException
	 * @throws DuplicateRecordException
	 */
	public static long add(UserBean bean) throws ApplicationException, DuplicateException {

		Connection conn = null;
		long pk = 0;

		UserBean existbean = findByLogin(bean.getLogin());

		if (existbean != null) {
			throw new DuplicateException("Login id already exist");

		}

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("INSERT INTO ST_USER VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pk = nextPk();
			ps.setLong(1, pk);
			ps.setString(2, bean.getFirstName());
			ps.setString(3, bean.getLastName());
			ps.setString(4, bean.getLogin());
			ps.setString(5, bean.getPass());
			ps.setDate(6, new Date(bean.getDob().getTime()));
			ps.setString(7, bean.getMobileNo());
			ps.setLong(8, bean.getRoleId());
			ps.setString(9, bean.getGender());
			ps.setString(10, bean.getCreatedBy());
			ps.setString(11, bean.getModifiedBy());
			ps.setTimestamp(12, bean.getCreatedDatetime());
			ps.setTimestamp(13, bean.getModifiedDatetime());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("Database Exception..",e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception:add roll back Exception" + ex.getMessage());

			}
			throw new ApplicationException("Exception in add user");

		} finally {
			JDBCDataSource.closeConn(conn);
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", bean.getLogin());
		map.put("password", bean.getPass());

		String message = EmailBuilder.getUserRegistrationMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(bean.getLogin());
		msg.setSubject("Registration is successful for ORS Project SunilOS");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);

		return pk;
	}

	/**
	 * change password
	 * 
	 * @param id
	 * @param oldPassword
	 * @param newPassword
	 * @return true and false
	 * @throws RecordNotFoundException
	 * @throws ApplicationException
	 */   
	public static void changePass(UserBean bean, String newPass) throws Exception {

		UserBean userExist = findByLogin(bean.getLogin());
		userExist.setPass(newPass);

		update(userExist);

		HashMap<String, String> map = new HashMap<String, String>();

		map.put("login", userExist.getLogin());
		map.put("password", userExist.getPass());
		map.put("firstName", userExist.getFirstName());
		map.put("lastName", userExist.getLastName());

		String msg = EmailBuilder.getChangePasswordMessage(map);

		EmailMessage email = new EmailMessage();

		email.setTo(bean.getLogin());
		email.setSubject("SUNARYS ORS Password has been changed Successfully.");
		email.setMessage(msg);
		email.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(email);

	}

	public static void reset(UserBean bean) throws Exception {

		UserBean userExist = findByLogin(bean.getLogin());

		if (userExist != null) {
			HashMap<String, String> map = new HashMap<String, String>();

			map.put("login", userExist.getLogin());
			map.put("password", userExist.getPass());
			map.put("firstName", userExist.getFirstName());
			map.put("lastName", userExist.getLastName());

			String msg = EmailBuilder.getForgetPasswordMessage(map);

			EmailMessage email = new EmailMessage();

			email.setTo(bean.getLogin());
			email.setSubject("SUNARYS ORS Password reset.");
			email.setMessage(msg);
			email.setMessageType(EmailMessage.HTML_MSG);

			EmailUtility.sendMail(email);
		}
	}

	/**
	 * delete user
	 * 
	 * @param bean
	 * @throws ApplicationException
	 */
	public static int delete(UserBean bean) throws Exception {

		int r = 0;

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement("DELETE FROM ST_USER WHERE ID = ?");
		ps.setLong(1, bean.getId());
		r = ps.executeUpdate();

		JDBCDataSource.closeConn(conn, ps);

		return r;
	}

	/**
	 * update user
	 * 
	 * @param bean
	 * @throws ApplicationException
	 * @throws DuplicateException 
	 * @throws SQLException 
	 */
	public static String update(UserBean bean) throws ApplicationException, DuplicateException, SQLException{
		String r = null;

		UserBean beanExist = findByLogin(bean.getLogin());

		System.out.println("cccccccccccccccccccccccccccccccccc update in model" + bean.getPass());
		if (beanExist != null && !(beanExist.getId() == bean.getId())) {
			throw new DuplicateException("Login Id already exist");
		}
		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement ps = conn.prepareStatement(
				"UPDATE ST_USER SET FIRST_NAME=?,LAST_NAME=?,LOGIN=?,PASSWORD=?,DOB=?,MOBILE_NO=?,ROLE_ID=?,GENDER=?, CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID = ?");
		ps.setLong(13, bean.getId());
		ps.setString(1, bean.getFirstName());
		ps.setString(2, bean.getLastName());
		ps.setString(3, bean.getLogin());
		ps.setString(4, bean.getPass());
		ps.setDate(5, new Date(bean.getDob().getTime()));
		ps.setString(6, bean.getMobileNo());
		ps.setLong(7, bean.getRoleId());
		ps.setString(8, bean.getGender());
		ps.setString(9, bean.getCreatedBy());
		ps.setString(10, bean.getModifiedBy());
		ps.setTimestamp(11, bean.getCreatedDatetime());
		ps.setTimestamp(12, bean.getModifiedDatetime());

		try {
			ps.executeUpdate();
			r = "Updated";
		} catch (Exception e) {
			e.getStackTrace();
		}

		JDBCDataSource.closeConn(conn, ps);

		return r;
	}

	public static ArrayList<UserBean> search(UserBean bean) throws Exception {
		return search(bean, 0, 0);
	}

	public static void main(String[] args) throws Exception {
		UserBean b = new UserBean();
		b.setFirstName("gaga");
		search(b);
	}

	/**
	 * search user
	 * 
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @return list
	 * @throws ApplicationException
	 */
	public static ArrayList<UserBean> search(UserBean bean, int pageNo, int pageSize) throws Exception {
		ArrayList<UserBean> list = null;

		StringBuffer sql = new StringBuffer(
				"SELECT ID,FIRST_NAME,LAST_NAME,LOGIN,PASSWORD,DOB,MOBILE_NO,ROLE_ID,GENDER, CREATED_BY, MODIFIED_BY, CREATED_DATETIME, MODIFIED_DATETIME FROM ST_USER WHERE TRUE");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND ID=" + bean.getId());
			}
			if (bean.getFirstName() != null && bean.getFirstName().length() > 0) {
				sql.append(" AND FIRST_NAME LIKE '" + bean.getFirstName() + "%'");
			}
			if (bean.getLogin() != null && bean.getLogin().length() > 0) {
				sql.append(" AND LOGIN LIKE '" + bean.getLogin() + "%'");
			}
			if (bean.getRoleId() > 0) {
				System.out.println("model  search  +" + bean.getRoleId());
				sql.append(" AND ROLE_ID ='" + bean.getRoleId() + "'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" Limit " + pageNo + ", " + pageSize);
		}

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql.toString());

		ResultSet rs = ps.executeQuery();
		list = new ArrayList<UserBean>();

		while (rs.next()) {
			bean = new UserBean();
			bean.setId(rs.getLong(1));
			bean.setFirstName(rs.getString(2));
			bean.setLastName(rs.getString(3));
			bean.setLogin(rs.getString(4));
			bean.setPass(rs.getString(5));
			bean.setDob(rs.getDate(6));
			bean.setMobileNo(rs.getString(7));
			bean.setRoleId(rs.getLong(8));
			bean.setGender(rs.getString(9));
			bean.setCreatedBy(rs.getString(10));
			bean.setModifiedBy(rs.getString(11));
			bean.setCreatedDatetime(rs.getTimestamp(12));
			bean.setModifiedDatetime(rs.getTimestamp(13));
			list.add(bean);
		}

		JDBCDataSource.closeConn(conn, ps, rs);
		return list;
	}

	public static ArrayList<UserBean> list() throws Exception {
		return list(0, 0);
	}

	 /**
	 * list of user
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return list
	 * @throws ApplicationException
	 */
	public static ArrayList<UserBean> list(int pageNo, int pageSize) throws Exception {

		StringBuffer sql = new StringBuffer(
				"SELECT ID,FIRST_NAME,LAST_NAME,LOGIN,PASSWORD,DOB,MOBILE_NO,ROLE_ID,GENDER, CREATED_BY, MODIFIED_BY, CREATED_DATETIME, MODIFIED_DATETIME FROM ST_USER");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" Limit " + pageNo + ", " + pageSize);
		}

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement ps = conn.prepareStatement(sql.toString());
		ArrayList<UserBean> l = new ArrayList<UserBean>();
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			UserBean bean = new UserBean();
			bean.setId(rs.getLong(1));
			bean.setFirstName(rs.getString(2));
			bean.setLastName(rs.getString(3));
			bean.setLogin(rs.getString(4));
			bean.setPass(rs.getString(5));
			bean.setDob(rs.getDate(6));
			bean.setMobileNo(rs.getString(7));
			bean.setRoleId(rs.getLong(8));
			bean.setGender(rs.getString(9));
			bean.setCreatedBy(rs.getString(10));
			bean.setModifiedBy(rs.getString(11));
			bean.setCreatedDatetime(rs.getTimestamp(12));
			bean.setModifiedDatetime(rs.getTimestamp(13));
			l.add(bean);
		}
		JDBCDataSource.closeConn(conn, ps, rs);
		return l;

	}

	/**
	 * find user by login
	 * 
	 * @param login
	 * @return bean
	 * @throws ApplicationException
	 */
	public static UserBean findByLogin(String login) throws ApplicationException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		UserBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			ps = conn.prepareStatement(
					"SELECT ID,FIRST_NAME,LAST_NAME,LOGIN,PASSWORD,DOB,MOBILE_NO,ROLE_ID,GENDER, CREATED_BY, MODIFIED_BY, CREATED_DATETIME, MODIFIED_DATETIME  FROM ST_USER WHERE LOGIN=?");
			ps.setString(1, login);
			rs = ps.executeQuery();
			bean = new UserBean();
			while (rs.next()) {
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPass(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setRoleId(rs.getLong(8));
				bean.setGender(rs.getString(9));
				bean.setCreatedBy(rs.getString(10));
				bean.setModifiedBy(rs.getString(11));
				bean.setCreatedDatetime(rs.getTimestamp(12));
				bean.setModifiedDatetime(rs.getTimestamp(13));
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception : in add College" + e.getMessage());
		} finally {
			JDBCDataSource.closeConn(conn, ps, rs);
		}

		return bean;
	}

	/**
	 * find user by pk
	 * 
	 * @param pk
	 * @return bean
	 * @throws ApplicationException
	 */
	public static UserBean findByPK(long id) throws Exception {
		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement(
				"SELECT ID,FIRST_NAME,LAST_NAME,LOGIN,PASSWORD,DOB,MOBILE_NO,ROLE_ID,GENDER, CREATED_BY, MODIFIED_BY, CREATED_DATETIME, MODIFIED_DATETIME FROM ST_USER WHERE ID=?");
		ps.setLong(1, id);
		ResultSet rs = ps.executeQuery();
		UserBean bean = new UserBean();
		while (rs.next()) {
			bean.setId(rs.getLong(1));
			bean.setFirstName(rs.getString(2));
			bean.setLastName(rs.getString(3));
			bean.setLogin(rs.getString(4));
			bean.setPass(rs.getString(5));
			bean.setDob(rs.getDate(6));
			bean.setMobileNo(rs.getString(7));
			bean.setRoleId(rs.getLong(8));
			bean.setGender(rs.getString(9));
			bean.setCreatedBy(rs.getString(10));
			bean.setModifiedBy(rs.getString(11));
			bean.setCreatedDatetime(rs.getTimestamp(12));
			bean.setModifiedDatetime(rs.getTimestamp(13));
		}
		JDBCDataSource.closeConn(conn, ps, rs);
		return bean;
	}

	/**
	 * authenticate user
	 * 
	 * @param login
	 * @param password
	 * @return bean
	 * @throws ApplicationException
	 */
	public static UserBean auth(String login, String pass) throws Exception {

		Connection con = JDBCDataSource.getConnection();
		PreparedStatement ps = con.prepareStatement(
				"SELECT ID,FIRST_NAME,LAST_NAME,LOGIN,PASSWORD,DOB,MOBILE_NO,ROLE_ID,GENDER, CREATED_BY, MODIFIED_BY, CREATED_DATETIME, MODIFIED_DATETIME FROM ST_USER WHERE LOGIN= ? AND PASSWORD= ?");

		ps.setString(1, login);
		ps.setString(2, pass);

		ResultSet rs = ps.executeQuery();
		UserBean bean = null;

		while (rs.next()) {
			bean = new UserBean();
			bean.setId(rs.getLong(1));
			bean.setFirstName(rs.getString(2));
			bean.setLastName(rs.getString(3));
			bean.setLogin(rs.getString(4));
			bean.setPass(rs.getString(5));
			bean.setDob(rs.getDate(6));
			bean.setMobileNo(rs.getString(7));
			bean.setRoleId(rs.getLong(8));
			bean.setGender(rs.getString(9));
			bean.setCreatedBy(rs.getString(10));
			bean.setModifiedBy(rs.getString(11));
			bean.setCreatedDatetime(rs.getTimestamp(12));
			bean.setModifiedDatetime(rs.getTimestamp(13));
		}

		JDBCDataSource.closeConn(con, ps, rs);

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

		ResultSet r = s.executeQuery("SELECT MAX(ID) FROM ST_USER");

		while (r.next()) {
			id = r.getLong(1);
		}
		r.close();
		s.close();
		conn.close();

		return id + 1;
	}

}
