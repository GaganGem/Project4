package in.co.rays.project4.models;

import in.co.rays.project4.beans.StudentBean;

public class TestModels {
	public static void main(String[] args) throws Exception {

		StudentBean b = new StudentBean();

//		b.setCollegeId(1);
//		b.setId(3);
//		b.setCollegeName("TRUBA");
//		b.setFirstName("mayank");
//		b.setLastName("dubey");
//		b.setEmail("gagansunhare");
//		b.setDob(DataUtility.getDate("12/11/1999"));
//		long a = 3;
		b = StudentModel.findByEmailId("gagansunhare");
		
		System.out.println(b.getFirstName());
			
	}
}
