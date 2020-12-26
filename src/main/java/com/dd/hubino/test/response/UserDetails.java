package com.dd.hubino.test.response;

import java.util.List;

import com.dd.hubino.test.entity.Address;

public class UserDetails {
	
	private String name;

	private String deptName;
	
	private String role;
	
	public List<Address> getAddress() {
		return address;
	}

	public void setAddress(List<Address> address) {
		this.address = address;
	}

	private List<Address> address;

	public UserDetails() {
		
	}

	public UserDetails(String name, String deptName, String role, List<Address> address) {
		super();
		this.name = name;
		this.deptName = deptName;
		this.role = role;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
	
	 


}
