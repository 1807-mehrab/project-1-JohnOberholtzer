package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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
@WebServlet("/loademployees")
public class LoadEmployeesServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		String username = (String) session.getAttribute("username");
		System.out.println("User loading tickets: " + username);

		ObjectMapper mapper = new ObjectMapper();
		PrintWriter pw = resp.getWriter();
		EmployeeDAO E_DAO = new EmployeeDAO(new ConnectionManager());
		List<Employee> Employees = E_DAO.getAllEmployees();
		for (Employee E : Employees) {
			System.out.println(E.toString());
		}
			
		if (!E_DAO.detectEmployee(username)) {
			session.invalidate();
		} else {
			String jsonString = mapper.writeValueAsString(Employees);
			System.out.println(jsonString);
			pw.println(jsonString);
		}
		pw.close();
	}
}
