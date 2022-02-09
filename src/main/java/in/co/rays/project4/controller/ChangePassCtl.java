package in.co.rays.project4.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import in.co.rays.project4.beans.BaseBean;
import in.co.rays.project4.beans.UserBean;
import in.co.rays.project4.models.UserModel;
import in.co.rays.project4.util.DataUtility;
import in.co.rays.project4.util.DataValidator;
import in.co.rays.project4.util.PropertyReader;
import in.co.rays.project4.util.ServletUtility;

/**
 * change password operation functionality perform
 * @author Gagan
 *
 */
@WebServlet(name = "ChangePassword", urlPatterns = { "/ctl/ChangePassCtl" })
public class ChangePassCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;

	public static final String OP_CHANGE_MY_PROFILE = "Change My Profile";

//	private static Logger log=Logger.getLogger(ChangePassCtl.class);
	
	@Override
	protected boolean validate(HttpServletRequest request) {
//		log.debug("ChangePasswordCtl Method validate Started");
		
		boolean pass = true;

		String op = request.getParameter("operation");
		if (OP_CHANGE_MY_PROFILE.equalsIgnoreCase(op)) {
			return pass;
		}
		if (DataValidator.isNull(request.getParameter("oldPass"))) {
			request.setAttribute("oldPass", PropertyReader.getValue("error.require", "Old Password"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("newPass"))) {
			request.setAttribute("newPass", PropertyReader.getValue("error.require", "New Password"));
			pass = false;
		}else if (!DataValidator.isPassword(request.getParameter("newPass"))) {
			request.setAttribute("newPass", "Please Enter vaild Password");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("newConPass"))) {
			request.setAttribute("newConPass", PropertyReader.getValue("error.require", "Confirm Password"));
			pass = false;
		} else if (!request.getParameter("newPass").equals(request.getParameter("newConPass"))) {
			request.setAttribute("passMatch", "Confirm Password must be same!");
			pass = false;
		}
//		log.debug("ChangePasswordCtl Method validate Ended");
		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
//		log.debug("ChangePasswordCtl Method populatebean Started");
		UserBean bean = new UserBean();
		HttpSession session = request.getSession();
		bean = (UserBean) session.getAttribute("user");
		bean.setPass(DataUtility.getString(request.getParameter("oldPass")));
		 populateDTO(bean, request);
//		 log.debug("ChangePasswordCtl Method populatebean Ended");
		return bean;
	}

	/**
     * Display Logics inside this method
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletUtility.forward(getView(), request, response);
	}

	/**
     * Submit logic inside it
     */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = request.getParameter("operation");
//		log.debug("ChangePasswordCtl Method doPost Started");
		
		if (OP_SAVE.equalsIgnoreCase(op)) {

			UserBean bean = (UserBean) populateBean(request);
			try {
				UserModel.changePass(bean, request.getParameter("newPass"));
					ServletUtility.setErrorMessage("Invalid Old Password!", request);
					ServletUtility.forward(getView(), request, response);
			}  catch (Exception e) {
				// log.error(e);
				ServletUtility.handleException(e, request, response);
				return;

			} 
			
			ServletUtility.forward(getView(), request, response);
		} else if (OP_CHANGE_MY_PROFILE.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.MY_PROFILE_CTL, request, response);
		}
//		log.debug("ChangePasswordCtl Method doGet Ended");
		
	}

	@Override
	protected String getView() {
		return ORSView.CHANGE_PASSWORD_VIEW;
	}

}
