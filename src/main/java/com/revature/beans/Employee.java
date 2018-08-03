package com.revature.beans;

public class Employee {
	private int E_ID;
	private String username;
	private String firstname;
	private String lastname;
	private String password;
	private String phone;
	private String address;
	private String email;
	private int manager;
	
	
	public Employee(int E_ID, int manager, String username, String firstname, String lastname, String password, String phone, String address,
			String email) {
		super();
		this.E_ID = E_ID;
		this.manager = manager;
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = password;
		
		this.phone = phone;
		this.address = address;
		this.email = email;
	}

	public Employee() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "Employee [E_ID=" + E_ID + ", username=" + username + ", firstname=" + firstname + ", lastname="
				+ lastname + ", password=" + password + ", phone=" + phone + ", address=" + address + ", email=" + email
				+ ", isManager=" + manager + "]";
	}

	public Integer getE_ID() {
		return E_ID;
	}
	
	public void setE_ID(int E_ID) {
		this.E_ID = E_ID;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public int getManager() {
		return manager;
	}

	public void setManager(int manager) {
		this.manager = manager;
	}

}
