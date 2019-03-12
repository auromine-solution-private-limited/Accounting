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
<%@ page errorPage="ExceptionHandler.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>report generation in  jsp</title>
</head>
<body>
<%
//String pbfrmdate=request.getParameter("pbfrmdate");
String jobordervoucherId=request.getParameter("jobordervoucherId");
//String supplierName=request.getParameter("supplierName");
/*

String filename =  request.getParameter("filename");
String reporttype =  request.getParameter("reporttype");
String Paramtype =  request.getParameter("paramtype");;
*/
String filename =  "/WEB-INF/reports/oldjewelToSmith.jasper";
String reporttype =  "pdf";
String Paramtype =  "";

Class.forName("com.mysql.jdbc.Driver");
Connection  conn =  DriverManager.getConnection("jdbc:mysql://localhost:3306/jewellery","root","admin");
System.out.println("Connection  Established");

Map  jasperParameter = new  HashMap();
jasperParameter.put("jobordervoucherId",jobordervoucherId);
//jasperParameter.put("pbfrmdate",pbfrmdate);
//jasperParameter.put("supplierName",supplierName);

String path =  application.getRealPath("/");
JasperPrint jasperPrint =  JasperFillManager.fillReport(path +"/"+filename, jasperParameter,  conn);
System.out.println("Report Created... in  "+reporttype +" Format");
OutputStream ouputStream =  response.getOutputStream();
JRExporter exporter = null;

if( "pdf".equalsIgnoreCase(reporttype)  )
{
response.setContentType("application/pdf");
response.setHeader("Content-Disposition",  "inline; filename=\"oldjewelbill.pdf\"");

exporter = new  JRPdfExporter();
exporter.setParameter(JRExporterParameter.JASPER_PRINT,  jasperPrint);
exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,  ouputStream);
}

else if(  "rtf".equalsIgnoreCase(reporttype)  )
{
response.setContentType("application/rtf");
response.setHeader("Content-Disposition",  "inline; filename=\"oldjewelbill.rtf\"");

exporter = new  JRRtfExporter();
exporter.setParameter(JRExporterParameter.JASPER_PRINT,  jasperPrint);
exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,  ouputStream);
}

else if(  "html".equalsIgnoreCase(reporttype)  )
{
response.setContentType("application/html");
response.setHeader("Content-Disposition",  "inline; filename=\"oldjewelbill.html\"");

exporter = new  JRHtmlExporter();
exporter.setParameter(JRExporterParameter.JASPER_PRINT,  jasperPrint);
exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,  ouputStream);
exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI,"image?image=");
}

else if( "xls".equalsIgnoreCase(reporttype)  )
{
response.setContentType("application/xls");
response.setHeader("Content-Disposition",  "inline; filename=\"oldjewelbill.xls\"");
exporter = new  JRXlsExporter();
exporter.setParameter(JRExporterParameter.JASPER_PRINT,  jasperPrint);
exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,  ouputStream);
}


else if(  "csv".equalsIgnoreCase(reporttype)  )
{
response.setContentType("application/csv");
response.setHeader("Content-Disposition",  "inline; filename=\"oldjewelbill.csv\"");

exporter = new  JRCsvExporter();
exporter.setParameter(JRExporterParameter.JASPER_PRINT,  jasperPrint);
exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,  ouputStream);
}

try
{
exporter.exportReport();
}
catch  (JRException e)
{
throw new  ServletException(e);
}
finally
{
if (ouputStream !=  null)
{
try
{
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