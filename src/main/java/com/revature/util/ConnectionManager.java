package com.revature.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;

public class ConnectionManager {
	private LinkedList<Connection> Connections; 
	
	public ConnectionManager() {
		Connections = new LinkedList<Connection>();
	}
	
	public int connectionCount() {
		return Connections.size();
	}
	
	public Connection makeConnection() {
		int tries = 0;
		Connection curr = null;
		while (curr==null) {
			try{
				curr = ConnectionUtil.getConnection();
			}catch (Exception ex) {
				tries +=1;
				if(tries < 50) System.out.print(" Tries: " + tries);
				ex.getMessage();
				curr = null;
			}
		}
		Connections.add(curr);
		if(Connections.size() > 20) {
			System.out.println("WARNING: LARGE NUMBER OF CONNECTIONS");
		}
		return curr;
		
	}
	
	
	public void Kill(Connection Curr) {
		Connections.remove(Curr);
		while(Curr != null) {
			try {
				Curr.close();
				Curr = null;
			} catch (SQLException ex){
				ex.getMessage();
			}
		}
	}
}
