<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.io.IOException"%>
<%@page import="net.sf.jasperreports.engine.JRException"%>
<%@page import="net.sf.jasperreports.engine.export.JRCsvExporter"%>
<%@page import="net.sf.jasperreports.engine.export.JRXlsExporter"%>
<%@page import="net.sf.jasperreports.engine.export.JRHtmlExporterParameter"%>
<%@page import="net.sf.jasperreports.engine.export.JRHtmlExporter"%>
<%@page import="net.sf.jasperreports.engine.export.JRRtfExporter"%>
<%@page import="net.sf.jasperreports.engine.JRExporterParameter"%>
<%@page import="net.sf.jasperreports.engine.export.JRPdfExporter"%>
<%@page import="net.sf.jasperreports.engine.JRExporter"%>
<%@page import="java.io.OutputStream"%>
<%@page import="net.sf.jasperreports.engine.JasperPrint"%>
<%@page import="net.sf.jasperreports.engine.JasperFillManager"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page errorPage="ExceptionHandler.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
 "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>Day Wise Stock Report</title>
</head>
<body>
<%
String FrmDate =  request.getParameter("frmDate");
String ToDate =  request.getParameter("toDate");

java.util.Date DayWiseStkFrmDate = new SimpleDateFormat("dd/MM/yyyy").parse(FrmDate);
java.util.Date DayWiseStkToDate = new SimpleDateFormat("dd/MM/yyyy").parse(ToDate);


String StockMetalType = request.getParameter("StockMetalType");
String filename = "";

if(StockMetalType.equalsIgnoreCase("All")){
filename ="/WEB-INF/reports/DayWiseStockReportOldMetals.jasper";
 
} else if(StockMetalType.equalsIgnoreCase("Bullion")){
filename ="/WEB-INF/reports/DayWiseStockReportBullionInd.jasper";

}else if(StockMetalType.equalsIgnoreCase("Ornament")){
filename ="/WEB-INF/reports/DayWiseStockReportInd.jasper";

}else if(StockMetalType.equalsIgnoreCase("OldMetal")){
filename ="/WEB-INF/reports/DayWiseStockReportOldMetalsInd.jasper";

}else if(StockMetalType.equalsIgnoreCase("LooseMetal")){
filename ="/WEB-INF/reports/DayWiseStockReportLooseMetalInd.jasper";

}else if(StockMetalType.equalsIgnoreCase("Untagged")){
filename ="/WEB-INF/reports/DayWiseStockReportUntaggedInd.jasper";

}else if(StockMetalType.equalsIgnoreCase("LotStock")){
filename ="/WEB-INF/reports/DayWiseStockReportLotStockInd.jasper";
}

String reporttype =  "pdf";
String Paramtype =  "";

Class.forName("com.mysql.jdbc.Driver");
Connection  conn =  DriverManager.getConnection("jdbc:mysql://localhost:3306/jewellery","root","admin");
System.out.println("Connection  Established");

Map  jasperParameter = new HashMap();
jasperParameter.put("DayWiseStkFrmDate",DayWiseStkFrmDate);
jasperParameter.put("DayWiseStkToDate",DayWiseStkToDate);

String path =  application.getRealPath("/");
JasperPrint jasperPrint =  JasperFillManager.fillReport(path +"/"+filename, jasperParameter,  conn);
System.out.println("Report Created... in  "+reporttype +" Format");
OutputStream ouputStream =  response.getOutputStream();
JRExporter exporter = null;

if( "pdf".equalsIgnoreCase(reporttype)  )
{
	response.setContentType("application/pdf");
	response.setHeader("Content-Disposition",  "inline; filename=\"DayWiseStockReport.pdf\"");
	
	exporter = new  JRPdfExporter();
	exporter.setParameter(JRExporterParameter.JASPER_PRINT,  jasperPrint);
	exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,  ouputStream);
} else if(  "rtf".equalsIgnoreCase(reporttype)  ) {
	response.setContentType("application/rtf");
	response.setHeader("Content-Disposition",  "inline; filename=\"DayWiseStockReport.rtf\"");
	
	exporter = new  JRRtfExporter();
	exporter.setParameter(JRExporterParameter.JASPER_PRINT,  jasperPrint);
	exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,  ouputStream);
} else if(  "html".equalsIgnoreCase(reporttype)  ) {
	response.setContentType("application/html");
	response.setHeader("Content-Disposition",  "inline; filename=\"DayWiseStockReport.html\"");
	
	exporter = new  JRHtmlExporter();
	exporter.setParameter(JRExporterParameter.JASPER_PRINT,  jasperPrint);
	exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,  ouputStream);
	exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI,"image?image=");
} else if( "xls".equalsIgnoreCase(reporttype)  ) {
	response.setContentType("application/xls");
	response.setHeader("Content-Disposition",  "inline; filename=\"DayWiseStockReport.xls\"");
	exporter = new  JRXlsExporter();
	exporter.setParameter(JRExporterParameter.JASPER_PRINT,  jasperPrint);
	exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,  ouputStream);
} else if(  "csv".equalsIgnoreCase(reporttype)  ) {
	response.setContentType("application/csv");
	response.setHeader("Content-Disposition",  "inline; filename=\"DayWiseStockReport.csv\"");
	
	exporter = new  JRCsvExporter();
	exporter.setParameter(JRExporterParameter.JASPER_PRINT,  jasperPrint);
	exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,  ouputStream);
}

try
{
	exporter.exportReport();
}catch  (JRException e) {
	throw new  ServletException(e);
}finally {
	if (ouputStream !=  null)
	{
		try {
			ouputStream.close();
			ouputStream.flush();
			out.clear();
			return;
		}
		catch (IOException  ex){}
	}
}
%>
</body>
</html>