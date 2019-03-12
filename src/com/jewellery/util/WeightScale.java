package com.jewellery.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;

import javax.comm.CommPortIdentifier;
import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;
import javax.comm.UnsupportedCommOperationException;


public class WeightScale implements SerialPortEventListener 
{
		
	SerialPort serialPort;
	    /** The port we're normally going to use. */
	private static final String PORT_NAMES[] = {      
		/*  "/dev/tty.usbserial-A9007UX1", //Mac OS X
	        "/dev/ttyUSB0", // Linux */
		"COM1", // Windows
	};
	
	public static String WeightData="";
	private BufferedReader input;
	
	@SuppressWarnings("unused")
	private OutputStream output;
	private static final int TIME_OUT = 2000;
	private static final int DATA_RATE = 9600;
	@SuppressWarnings("unused")
	public void initialize() {
	
	    CommPortIdentifier portId = null;
	    
		try {
			portId = CommPortIdentifier.getPortIdentifier("COM1");
			//System.out.println(portId.getName());
	        
	        String type;
	        switch (portId.getPortType()) 
	        {
		        case CommPortIdentifier.PORT_PARALLEL:
		          type = "Parallel";
		          break;
		        case CommPortIdentifier.PORT_SERIAL:
		          type = "Serial";
		          break;
		        default: /// Shouldn't happen
		          type = "Unknown";
		          break;
	        }
	        
	       // System.out.println("port Name :"+portId.getName());
	       // System.out.println("Owner :"+portId.getCurrentOwner()+ " Port Type :" + type + "isCurrentlyOpened() :"+portId.isCurrentlyOwned());
	        
		} catch (NoSuchPortException e1) {
			System.out.println("NoSuchPortException");
		}
	     
	    Enumeration<?> portEnum = CommPortIdentifier.getPortIdentifiers();
	    
	    //First, Find an instance of serial port as set in PORT_NAMES.
	    while (portEnum.hasMoreElements()) 
	    {
	        CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
	        //System.out.println(currPortId.getName());
	        
	        String type;
	        switch (currPortId.getPortType()) {
	        case CommPortIdentifier.PORT_PARALLEL:
	          type = "Parallel";
	          break;
	        case CommPortIdentifier.PORT_SERIAL:
	          type = "Serial";
	          break;
	        default: /// Shouldn't happen
	          type = "Unknown";
	          break;
	        }
	        
	        //System.out.println("port Name :"+currPortId.getName());
	        //System.out.println("Owner :"+currPortId.getCurrentOwner()+ " Port Type :" + type + "isCurrentlyOpened() :"+currPortId.isCurrentlyOwned());
	        
	        for (String portName : PORT_NAMES) {
	            if (currPortId.getName().equals(portName)) {
	                portId = currPortId;
	                break;
	            }
	        }
	    }
	    
	    if (portId == null) {
	        System.out.println("Could not find COM port.");
	        return;
	    }
	
	    try {
	        serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);
	        serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
	
	        // open the streams
	        input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
	        output = serialPort.getOutputStream();
	
	        serialPort.addEventListener(this);
	        serialPort.notifyOnDataAvailable(true);
	        
	        
	    } catch(UnsupportedCommOperationException ue){
	    	  serialPort.removeEventListener();
              serialPort.close();
	    	System.err.println(ue.toString());
	    	//ue.printStackTrace();
	    	
	    } catch (PortInUseException e) {
	    	System.err.println(e.toString());
	    	 if (serialPort != null) {
	 	       serialPort.removeEventListener();
	 	       //serialPort.close();
	 	    }
		} catch (Exception e) {
			System.err.println(e.toString());
			//e.printStackTrace();
		}
	}
	
	public synchronized void close() 
	{
	    if (serialPort != null) {
	        serialPort.removeEventListener();
	        serialPort.close();
	    }
	}

	public synchronized void serialEvent(SerialPortEvent oEvent) 
	{
	    if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
	        try {
	            String inputLine=null;
	            if (input.ready()) {
	            	
	               inputLine = input.readLine();
	               inputLine= inputLine.replace("S", "");
	               inputLine= inputLine.replace("D", "");
	               inputLine= inputLine.replace("g", "");
	               
	               //System.out.println("In Event :"+inputLine.trim()+":");
	               //@SuppressWarnings("unused")
	               //Float fsWeight = Float.parseFloat(inputLine.trim());
	               //WeightScale.WeightData = inputLine.trim();
	               WeightScale.WeightData = inputLine.trim();
	               
	               serialPort.removeEventListener();
	               serialPort.close();
	            }
	        } catch (NumberFormatException e) {
	            //System.err.println(e.toString());
	        } catch (Exception e) {
	            System.err.println(e.toString());
	        }
	    }
	   // Ignore all the other eventTypes, but you should consider the other ones.
	}
	
}