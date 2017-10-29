package com.nextgen.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class LoginDetails {
	
	@Id
	@GeneratedValue
	private String login_id;
	
	private String username;
	private String password;
	
	private long parent_id;
	
	public String getLogin_id() {
		return login_id;
	}
	
	public String getPassword() {
		return password;
	}
		
	public String getUsername() {
		return username;
	}
	
	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public long getParent_id() {
		return parent_id;
	}

	public void setParent_id(long parent_id) {
		this.parent_id = parent_id;
	}
}
