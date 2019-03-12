package com.jewellery.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class JobScheduleController extends QuartzJobBean {
	  protected void executeInternal(JobExecutionContext ctx) throws JobExecutionException {
	      // check FTP
		  try {
		    	
			  	Runtime rt = Runtime.getRuntime();
		        DateFormat dateFormat = new SimpleDateFormat("dd_MM_YYYY_hh.mm.ss");
		        Date currentDate = new Date();
		        String backupFileName = "RJMS_"+dateFormat.format(currentDate)+".sql";
		    	//rt.exec("cmd.exe /c  mysqldump -u root -padmin -B jewellery > C:/backup/"+backupFileName);
		       	rt.exec("cmd.exe /c start C:/Backup.bat ");
		    	String name = ctx.getJobDetail().getName();
		    	String group = ctx.getJobDetail().getGroup();
		    	System.out.println("Job name: " + name + "\t" + "Group: " + group);
		    	System.out.println("Trigger name: " + ctx.getTrigger().getName());
		    	System.out.println("Firing Time: " + ctx.getFireTime());
		    	
	    	}catch(Exception ex){
	    		ex.printStackTrace();
	    	}
	  }
}
