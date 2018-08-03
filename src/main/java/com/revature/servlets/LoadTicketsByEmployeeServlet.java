package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.beans.Ticket;
import com.revature.dao.TicketDAO;
import com.revature.util.ConnectionManager;

@SuppressWarnings("serial")
@WebServlet("/loadticketsbyemployee")
public class LoadTicketsByEmployeeServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		int EID = mapper.readValue(req.getInputStream(), Integer.class);
		PrintWriter pw = resp.getWriter();
		TicketDAO T_DAO = new TicketDAO(new ConnectionManager());
	
		List<Ticket> Tickets = T_DAO.getTicketsByUser(EID);
			
		String jsonString = mapper.writeValueAsString(Tickets);
		System.out.println(jsonString);
		pw.println(jsonString);
		pw.close();
	}
}
