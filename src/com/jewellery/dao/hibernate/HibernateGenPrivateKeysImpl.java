package com.jewellery.dao.hibernate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import com.jewellery.dao.GenPrivateKeysDao;

public class HibernateGenPrivateKeysImpl extends HibernateDaoSupport implements GenPrivateKeysDao  {
	
	public String getSNAMS(String drive){
		String result = "";
		try {
			File file = File.createTempFile("realhowto", ".vbs");
			file.deleteOnExit();
			FileWriter fw = new java.io.FileWriter(file);
			String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
					+ "Set colDrives = objFSO.Drives\n"
					+ "Set objDrive = colDrives.item(\""
					+ drive
					+ "\")\n"
					+ "Wscript.Echo objDrive.SerialNumber"; // see note
			fw.write(vbs);
			fw.close();
			Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while ((line = input.readLine()) != null) {
				result += line;
			}
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return AMSCF(result).trim();
    }

	public String AMSCF(String tempVal)
    {
		StringBuffer hexVal = new StringBuffer("");
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			 md.update(tempVal.getBytes());
		        byte byteData[] = md.digest();
		        
		    	for (int i=0;i<byteData.length;i++) {
		    		String hex=Integer.toHexString(0xff & byteData[i]);
		   	     	if(hex.length()==1) hexVal.append('0');
		   	     	hexVal.append(hex);
		    	}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
    	return hexVal.toString();
    }
	
	public String getMainVal(String Type, String RCName){
		String sey = "";
		Resource resource = new ClassPathResource("/labels.properties");
		try {
			Properties props = PropertiesLoaderUtils.loadProperties(resource);
			if(Type.equalsIgnoreCase("Full Version")){
				sey = getSNAMS("C")+AMSCF(RCName)+props.getProperty("Product.prop").trim();
			}else{
				sey = getSNAMS("C")+AMSCF(RCName)+props.getProperty("Product.omed").trim();
			}
		}catch (IOException e) {
			System.out.println("prop");
			e.printStackTrace();
		}
		return AMSCF(sey);
	}
	
	 /* public String getMSNAMS(){
		StringBuilder finalMacAd = new StringBuilder("");
		try {
			InetAddress address = InetAddress.getLocalHost();
			NetworkInterface ni = NetworkInterface.getByInetAddress(address);
			
			if (ni != null) {
				byte[] mac = ni.getHardwareAddress();
				if (mac != null) {
					for (int i = 0; i < mac.length; i++) {
						finalMacAd.append(mac[i]);
					}
				} else {
					System.out.println("Address doesn't exist or is not accessible.");
				}
			} else {
				System.out.println("Network Interface for the specified address is not found.");
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return AMSCF(finalMacAd.toString());
	}*/
}
