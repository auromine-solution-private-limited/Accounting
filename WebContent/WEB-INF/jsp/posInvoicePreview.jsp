<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1" import="java.io.*, java.net.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>JSP Reading Text File</title>
</head>
<body>
<script language="javascript"  type="text/javascript" src="script/jquery-latest.js"></script> 
<script language="javascript" type="text/javascript" > 

	$(document).ready(function() {	
		window.print();
	});
</script>

<%
if(session.getAttribute("username")==null)
{
response.sendRedirect("login.htm"); // GO TO LOGIN PAGE
}
try
{
	String fileName = request.getSession().getServletContext().getRealPath("/")+"WEB-INF\\reports\\"+session.getAttribute("username")+"_POSInvoice8.txt";
	BufferedReader br = new BufferedReader(new FileReader(fileName));
	String data;
	while((data = br.readLine())!= null)
	{
		out.println(data+"<br>");
	}
}catch(IOException e)
{
	out.println("POSInvoicePreview View:"+e.getMessage());
}
%>
</body>
</html>