package com.revature.beans;

import java.sql.Blob;

public class Image {
	private Integer I_ID;
	private Integer T_ID;
	private Blob IMAGE;
	
	public Image() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Image(Integer i_ID, Integer t_ID, Blob iMAGE) {
		super();
		I_ID = i_ID;
		T_ID = t_ID;
		IMAGE = iMAGE;
	}

	public Integer getI_ID() {
		return I_ID;
	}

	public void setI_ID(Integer i_ID) {
		I_ID = i_ID;
	}

	public Integer getT_ID() {
		return T_ID;
	}

	public void setT_ID(Integer t_ID) {
		T_ID = t_ID;
	}

	public Blob getIMAGE() {
		return IMAGE;
	}

	public void setIMAGE(Blob iMAGE) {
		IMAGE = iMAGE;
	}
	
}
