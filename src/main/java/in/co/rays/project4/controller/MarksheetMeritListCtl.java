package in.co.rays.project4.controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project4.beans.BaseBean;
import in.co.rays.project4.beans.MarksheetBean;
import in.co.rays.project4.models.MarksheetModel;
import in.co.rays.project4.util.ServletUtility;

/**
 *  marksheetmerit list functionlity controller to show merit list student
 * @author Gagan
 *
 */
@WebServlet(name="MarksheetMeritListCtl",urlPatterns={"/ctl/MarksheetMeritListCtl"})
public class MarksheetMeritListCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
       
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		return null;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		ArrayList<MarksheetBean> list = null;
		try {
			list = (ArrayList<MarksheetBean>) MarksheetModel.meritList();
		} catch (Exception e) {
			ServletUtility.handleException(e, request, response);
			return;
		}
		ServletUtility.setList(list, request);
		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if(OP_BACK.equalsIgnoreCase(request.getParameter("operation"))){
			ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);
			return;
		}
		
		ArrayList<MarksheetBean> list = null;
		
		try {
			list = (ArrayList<MarksheetBean>) MarksheetModel.meritList();
		} catch (Exception e) {
			ServletUtility.handleException(e, request, response);
			return;
		}
		 if (list == null || list.size() == 0) {
             ServletUtility.setErrorMessage("No record found ", request);
         }
		ServletUtility.setList(list, request);
		ServletUtility.forward(getView(), request, response);
		
	}

	@Override
	protected String getView() {
		return ORSView.MARKSHEET_MERIT_LIST_VIEW;
	}

}
