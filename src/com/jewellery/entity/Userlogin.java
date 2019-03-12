package com.jewellery.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


@Entity
@NamedQueries({	
    @NamedQuery(name = "listUserlogin", query = "from com.jewellery.entity.Userlogin"),
    @NamedQuery(name = "getUserlogin", query = "from Userlogin where userName = ? and password=? and active='enable'"), 
    @NamedQuery(name ="userList", query="from Userlogin order by fullName"),
    @NamedQuery(name ="userNameList", query="from Userlogin where userName = ? ")
})
public class Userlogin implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer Id;
	private String userName;
	private String password;
	private String confirmPassword;
	private String fullName;
	private String emailId;
	private String rollName;
	private String active;
	
	public Userlogin (){
		
	}
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		
		return password;
	}

	public void setPassword(String password) {
				this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getRollName() {
		return rollName;
	}

	public void setRollName(String rollName) {
		this.rollName = rollName;
	}
	
	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
}
