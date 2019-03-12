package com.jewellery.dao;

import java.util.List;

import com.jewellery.entity.Userlogin;

public interface UserloginDao {

	public List<Userlogin> listUserlogin();
	public List<Userlogin> searchlogin(String username,String password);
	public List<Userlogin> userlist();
	public List<Userlogin> searchUserName(String userName);
	public Userlogin getUserlogin(Integer id);
	public void insertUserlogin(Userlogin userlogin);
	public void updateUserlogin(Userlogin userlogin);
	public void deleteUserlogin(Userlogin userlogin);
	public List<Userlogin> searchByFullName(String fullName);
}
