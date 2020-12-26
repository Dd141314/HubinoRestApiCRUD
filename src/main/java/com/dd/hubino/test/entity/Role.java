package com.dd.hubino.test.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role {

	@Id
	@Column(name = "id")
	private  int id;
	
	
	@Column(name = "role_name")
	private String roleName;


	public Role() {
		
	}

	public Role(String roleName) {
		super();
		this.roleName = roleName;
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getRoleName() {
		return roleName;
	}


	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	
	

	
	
}
