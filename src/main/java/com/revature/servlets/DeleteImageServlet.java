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
import com.revature.beans.Image;
import com.revature.dao.ImageDAO;
import com.revature.util.ConnectionManager;

@SuppressWarnings("serial")
@WebServlet("/delimage")
public class DeleteImageServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		int IID = mapper.readValue(req.getInputStream(), Integer.class);
		ImageDAO IM_DAO = new ImageDAO(new ConnectionManager());
		IM_DAO.delImage(IID);
	}
}
