package in.co.rays.project4.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project4.beans.BaseBean;
import in.co.rays.project4.beans.CourseBean;
import in.co.rays.project4.beans.SubjectBean;
import in.co.rays.project4.beans.TimetableBean;
import in.co.rays.project4.exception.DuplicateException;
import in.co.rays.project4.models.CourseModel;
import in.co.rays.project4.models.SubModel;
import in.co.rays.project4.models.TimetableModel;
import in.co.rays.project4.util.DataUtility;
import in.co.rays.project4.util.DataValidator;
import in.co.rays.project4.util.PropertyReader;
import in.co.rays.project4.util.ServletUtility;

/**
 * Timetable functionality controller. to perform add,delete and update
 * operation
 * 
 * @author Gagan
 *
 */
@WebServlet(name="TimetableCtl",urlPatterns={"/ctl/TimeTableCtl"})
public class TimeTableCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;
	
	protected void preload(HttpServletRequest request) {
		List<CourseBean> clist = new ArrayList<CourseBean>();
		List<SubjectBean> slist = new ArrayList<SubjectBean>();
		try {
			clist = CourseModel.list();
			slist = SubModel.list();
			request.setAttribute("CourseList", clist);
			request.setAttribute("SubjectList", slist);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected boolean validate(HttpServletRequest request) {
		//log.debug("validate method of TimeTable Ctl started");
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("courseId"))) {
			request.setAttribute("courseId", PropertyReader.getValue("error.require", "Course"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("subjectId"))) {
			request.setAttribute("subjectId", PropertyReader.getValue("error.require", "Subject"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("semester"))) {
			request.setAttribute("semester", PropertyReader.getValue("error.require", "Semester"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("ExDate"))) {
			request.setAttribute("ExDate", PropertyReader.getValue("error.require", "Exam Date"));
			pass = false;
		}else if (!DataValidator.isDate(request.getParameter("ExDate"))) {
			request.setAttribute("examDate", PropertyReader.getValue("error.date", "Exam Date"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("Extime"))) {
			request.setAttribute("Extime", PropertyReader.getValue("error.require", "Exam Time"));
			pass = false;
		}
		return pass;
	}
	
	protected TimetableBean populateBean(HttpServletRequest request) {

		TimetableBean bean = new TimetableBean();

			bean.setId(DataUtility.getInt(request.getParameter("id")));
			
			bean.setSubId(DataUtility.getInt(request.getParameter("subjectId")));

			bean.setCourseId(DataUtility.getInt(request.getParameter("courseId")));

			bean.setSem(DataUtility.getString(request.getParameter("semester")));

			bean.setExamDate(DataUtility.getDate(request.getParameter("ExDate")));

			bean.setExamTime(DataUtility.getString(request.getParameter("Extime")));
			populateDTO(bean, request);
			return bean;
		}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = DataUtility.getInt(request.getParameter("id"));
		
		TimetableBean bean = null;
		if (id > 0) {
			try {
				bean = TimetableModel.findByPk(id);
				ServletUtility.setBean(bean, request);
			} catch (Exception e) {
			//	log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List list;
		String op = DataUtility.getString(request.getParameter("operation"));
		int id = DataUtility.getInt(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) 
		{
			TimetableBean bean = (TimetableBean)populateBean(request);
			try {
				if(id>0){
					TimetableModel.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage(" TimeTable is Successfully Updated", request);
					
				}else{

				TimetableModel.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage(" TimeTable is Successfully Added", request);
				} 
			}catch (DuplicateException e1) {
				ServletUtility.setErrorMessage(e1.getMessage(), request);
				ServletUtility.forward(getView(), request, response);
				return;
			} catch (Exception e) {
				//log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			} 
		}
		else if (OP_CANCEL.equalsIgnoreCase(op))
		{
			ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);
			return;
		}
		else if ( OP_RESET.equalsIgnoreCase(op))
		{
			ServletUtility.redirect(ORSView.TIMETABLE_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
	}
	

	@Override
	protected String getView() {
		return ORSView.TIMETABLE_VIEW;
	}

}
