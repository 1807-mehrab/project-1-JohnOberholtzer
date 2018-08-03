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
import com.revature.beans.Ticket;
import com.revature.dao.EmployeeDAO;
import com.revature.dao.TicketDAO;
import com.revature.util.ConnectionManager;

@SuppressWarnings("serial")
@WebServlet("/loadalltickets")
public class LoadAllTicketsServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		String username = (String) session.getAttribute("username");
		System.out.println("User loading all tickets: " + username);

		ObjectMapper mapper = new ObjectMapper();
		PrintWriter pw = resp.getWriter();
		EmployeeDAO E_DAO = new EmployeeDAO(new ConnectionManager());
		TicketDAO T_DAO = new TicketDAO(new ConnectionManager());
		List<Ticket> Tickets = T_DAO.getAllTickets();
			
		if (!E_DAO.detectEmployee(username)) {
			session.invalidate();
		} else {
			String jsonString = mapper.writeValueAsString(Tickets);
			System.out.println(jsonString);
			pw.println(jsonString);
		}
		pw.close();
	}
}
