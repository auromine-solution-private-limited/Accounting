package com.jewellery.dao;

import java.util.List;
import com.jewellery.entity.JobOrder;

public interface JobOrderDao {
	public List<JobOrder> listJobOrder();
	public List<JobOrder> searchJobOrder(Integer order);
	public JobOrder getJobOrder(Integer id);
	public JobOrder getJobOrder(String smithname);
	public List<JobOrder> searchSmith(String smithName);
	public void insertJobOrder(JobOrder joborder);
	public void updateJobOrder(JobOrder joborder);
	public void deleteJobOrder(JobOrder joborder);
	
	// Auto Complete JobOrder : Smith Name Search Box  
	public List<String> getAutoSmithName(String smithNamePart);
	
}
