<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   "http://www.w3.org/TR/html4/loose.dtd">  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" href="default.css" type="text/css"></link>
<base href="${pageContext.request.contextPath}">

<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>Jewel Mine</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
</head>
<body>
      <%

      if(session.getAttribute("username")==null)
	    
      response.sendRedirect("login.htm");
   
      else

      {
   
      session.setAttribute("username",null);
 
      session.invalidate();
  
      response.sendRedirect("login.htm");
  
      }

      %>

</body>		

</html>