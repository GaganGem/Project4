package in.co.rays.project4.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.co.rays.project4.beans.BaseBean;
import in.co.rays.project4.beans.UserBean;
import in.co.rays.project4.models.UserModel;
import in.co.rays.project4.util.DataUtility;
import in.co.rays.project4.util.DataValidator;
import in.co.rays.project4.util.PropertyReader;
import in.co.rays.project4.util.ServletUtility;
import in.co.rays.project4.controller.*;

/**
 * login functionality controller. perform login operation
 * @author Gagan
 *
 */
@WebServlet(name = "LoginCtl", urlPatterns = { "/LoginCtl" })
public class LoginCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	public static final String OP_REGISTER = "register";
	public static final String OP_SIGN_IN = "Sign In";
	public static final String OP_SIGN_UP = "Sign Up";
	public static final String OP_LOG_OUT = "logout";

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		String op = request.getParameter("operation");
		if (OP_SIGN_UP.equals(op) || OP_LOG_OUT.equals(op)) {
			return pass;
		}

		String login = request.getParameter("login");

		if (DataValidator.isNull(login)) {
			request.setAttribute("login", PropertyReader.getValue("error.require", "Login Id"));
			pass = false;
		} else if (!DataValidator.isEmail(login)) {
			request.setAttribute("login", PropertyReader.getValue("error.email", "Login "));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("password"))) {
			request.setAttribute("password", PropertyReader.getValue("error.require", "Password"));
			pass = false;
		}
		System.out.println("validate");
		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		UserBean bean = new UserBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setLogin(DataUtility.getString(request.getParameter("login")));
		bean.setPass(DataUtility.getString(request.getParameter("password")));
		populateDTO(bean, request);
		System.out.println("populate");

		return bean;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = request.getParameter("operation");
		HttpSession session = request.getSession();
		if (OP_LOG_OUT.equalsIgnoreCase(op)) {
			session.invalidate();
			ServletUtility.setSuccessMessage("You Have Successfully Logout", request);
		}
		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = request.getParameter("operation");

		if (OP_SIGN_IN.equalsIgnoreCase(op)) {

			UserBean bean = (UserBean) populateBean(request);
			System.out.println("populateif");

			try {

				UserBean beanExist = null;
				beanExist = UserModel.findByLogin(bean.getLogin());
				if (beanExist == null) {
					ServletUtility.setErrorMessage("Email Id Doesn't Exist, Please Register!", request);
					ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);
					return;
				}

				bean = UserModel.auth(bean.getLogin(), bean.getPass());
				String uri = (String) request.getParameter("uri");
				
				if (bean != null) {

					HttpSession session = request.getSession(true);
					session.setAttribute("user", bean);

//					ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);
					if (uri == null || "null".equalsIgnoreCase(uri)) {
						System.out.println("uri==null");
						ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);
						return;
					} else {

						System.out.println("rolebean.getId");
						ServletUtility.redirect(uri, request, response);
						return;
					} 
					
					
				} else {
					System.out.println("erroeoex");
					bean = (UserBean) populateBean(request);
					ServletUtility.setErrorMessage("Invalid Id & Password!", request);
					ServletUtility.setBean(bean, request);
					ServletUtility.forward(ORSView.LOGIN_VIEW, request, response);
				}

				System.out.println(">>>>>>>>>>>>>>>>>>>>>" + uri);


			} catch (Exception e) {
				ServletUtility.handleException(e, request, response);
				return;
			}
		//	ServletUtility.setBean(bean, request);
		} else if (op.equalsIgnoreCase(OP_SIGN_UP)) {
			ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);

		} else if (OP_LOG_OUT.equals(op)) {
			HttpSession session = request.getSession();
			session.invalidate();
			ServletUtility.redirect(ORSView.LOGIN_CTL, request, response);

			return;

		}
	}

	@Override
	protected String getView() {
		return ORSView.LOGIN_VIEW;
	}

}
