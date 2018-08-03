package com.revature.beans;

public class Ticket {
	private int T_ID;
	private int E_ID;
	private String T_TITLE;
	private String T_DESC;
	private int R_BOOLEAN;
	private int RM_ID;
	
	public Ticket() {
		// TODO Auto-generated constructor stub
	}

	public Ticket(int t_ID, int e_ID, String t_TITLE, String t_DESC, int r_BOOLEAN, int rM_ID) {
		super();
		T_ID = t_ID;
		E_ID = e_ID;
		T_TITLE = t_TITLE;
		T_DESC = t_DESC;
		R_BOOLEAN = r_BOOLEAN;
		RM_ID = rM_ID;
	}

	public int getT_ID() {
		return T_ID;
	}

	public void setT_ID(int t_ID) {
		T_ID = t_ID;
	}

	public int getE_ID() {
		return E_ID;
	}

	public void setE_ID(int e_ID) {
		E_ID = e_ID;
	}

	public String getT_TITLE() {
		return T_TITLE;
	}

	public void setT_TITLE(String t_TITLE) {
		T_TITLE = t_TITLE;
	}

	public String getT_DESC() {
		return T_DESC;
	}

	public void setT_DESC(String t_DESC) {
		T_DESC = t_DESC;
	}

	public int getR_BOOLEAN() {
		return R_BOOLEAN;
	}

	public void setR_BOOLEAN(int r_BOOLEAN) {
		R_BOOLEAN = r_BOOLEAN;
	}

	public int getRM_ID() {
		return RM_ID;
	}

	public void setRM_ID(int rM_ID) {
		RM_ID = rM_ID;
	}

}
