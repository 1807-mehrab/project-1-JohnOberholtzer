package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.beans.Employee;
import com.revature.dao.EmployeeDAO;
import com.revature.util.ConnectionManager;

@SuppressWarnings("serial")
@WebServlet("/updateprofile")
public class UpdateProfileServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		String UN = (String) session.getAttribute("username");
		System.out.println("User updating profile: " + UN);
		
		EmployeeDAO E_DAO = new EmployeeDAO(new ConnectionManager());
		ObjectMapper mapper = new ObjectMapper();
		Employee EMP = mapper.readValue(req.getInputStream(), Employee.class);
		
		Employee newEMP = E_DAO.getEMP(UN);
		newEMP.setFirstname(EMP.getFirstname());
		newEMP.setLastname(EMP.getLastname());
		newEMP.setPassword(EMP.getPassword());
		newEMP.setPhone(EMP.getPhone());
		newEMP.setAddress(EMP.getAddress());
		newEMP.setEmail(EMP.getEmail());
		E_DAO.updateEmployee(newEMP);
	}
}
