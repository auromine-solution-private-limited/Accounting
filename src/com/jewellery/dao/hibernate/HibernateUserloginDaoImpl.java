package com.jewellery.dao.hibernate;

import java.util.List;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import com.jewellery.dao.UserloginDao;
import com.jewellery.entity.Userlogin;

public class HibernateUserloginDaoImpl extends HibernateDaoSupport implements UserloginDao {

	public static final String LIST_BY_Userlogin = "listUserlogin";
	public static final String GET_Users = "getUserlogin";
	public static final String GET_USER_LIST="userList";
	public static final String GET_USER_NAME="userNameList";
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Userlogin> listUserlogin(){
		return (List<Userlogin>)getHibernateTemplate().findByNamedQuery(LIST_BY_Userlogin);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Userlogin> searchlogin(String username,String password){
		Object[] obj = {username,password};
		List<Userlogin> logins=getHibernateTemplate().findByNamedQuery(GET_Users,obj);
		if(logins !=null)
		{
			return logins;
		}
		return null;
	}
	

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Userlogin> searchUserName(String userName)
	{
		return (List<Userlogin>)getHibernateTemplate().findByNamedQuery(GET_USER_NAME, userName); 	
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Userlogin> searchByFullName(String fullName)
	{
		return (List<Userlogin>)getHibernateTemplate().find("FROM Userlogin WHERE fullName = ?",fullName); 	
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)

	public List<Userlogin>userlist()
	{
	return (List<Userlogin>)getHibernateTemplate().findByNamedQuery(GET_USER_LIST); 	
	}
	
	@Transactional
	public Userlogin getUserlogin(Integer id){
		System.out.println("Method get Userlogin by Id::::::::::::::::::::::::::::");

		if(getHibernateTemplate()!=null){
			return (Userlogin)getHibernateTemplate().get(Userlogin.class, id);
		}else{
	//	System.out.println("getHibernateTemplate() returns null value");
		return (Userlogin)getHibernateTemplate().get(Userlogin.class, id);
		}	
	}
	
	@Transactional
	public void insertUserlogin(Userlogin userlogin){
		System.out.println("Userlogin: Executing insert method ::::::::::::");
		getHibernateTemplate().saveOrUpdate(userlogin);	
	}
	
	@Transactional
	public void updateUserlogin(Userlogin userlogin){
		System.out.println("Userlogin: Executing update method ::::::::::::");
		getHibernateTemplate().update(userlogin);
	}
	
	@Transactional
	public void deleteUserlogin(Userlogin userlogin){
		System.out.println("Userlogin: Executing update method ::::::::::::");
		getHibernateTemplate().delete(userlogin);
	}
	
	
}
