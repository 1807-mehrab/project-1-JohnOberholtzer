package com.revature.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.beans.Employee;
import com.revature.dao.EmployeeDAO;
import com.revature.util.ConnectionManager;

@SuppressWarnings("serial")
@WebServlet("/loadspecificprofile")
public class LoadSpecificProfileServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		EmployeeDAO E_DAO = new EmployeeDAO(new ConnectionManager());
		
		PrintWriter pw = resp.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		InputStream INSTR = req.getInputStream();
		int EID = 0; 
		EID = mapper.readValue(INSTR, Integer.class);
		INSTR.close();
		Employee EMP = E_DAO.getEMPbyID(EID);
		System.out.print("Ticket Lookup Username =" + EMP.getUsername());

			String jsonString = mapper.writeValueAsString(EMP);
			pw.println(jsonString);
		
		pw.close();
	}
}
