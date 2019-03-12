<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language ="java" import="java.sql.*" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Jewel Mine</title>
<link rel="stylesheet" type="text/css" href="default.css" />
<script type="text/javascript" src="script/splashpage.js"></script>
<script type="javascript" >
window.onload = function()
{
	alert("Enter Login name & Password");
	document.getElementById('name').focus();

}
</script>

</head>
<body id="body">
<div id="login">
    	<div id="log_bg">
            <div id="log">
            <div id="logo_1">
            </div>
            	<div id="logo_1">
    				<img src="images/logo.png" />	
            	</div>
            	<table width="250" border="0" cellspacing="0" cellpadding="0" align="center">
  	                <tr>
    					<td>&nbsp;<br /><br /></td>
				     	<td>&nbsp;</td>
  					</tr>
                  	
                  	<tr>
    				<td> <form name="f1" method="post">
						<label>Name
   						<input name="t1" type="text" id="name" style="margin-left:35px;"/>
						</label><br />
						<label>Password
							<input type="password" name="t2" id="password" style="margin-top:5px; margin-left:10px" />
      					</label><br />
      					<label>
						<input type="image" name="b1" src="images/button.png" id="button" value="LogIn" style="margin-top:15px; margin-left:130px"/>
                      	</label>
      			 	</form> 
    		  		</td> 
    				<td>&nbsp;</td>
  					</tr>
			  </table>
            </div>
         </div>   
     </div>   
 
<%

String user=request.getParameter("t1");
String pass=request.getParameter("t2");

     try{
    
     Class.forName("com.mysql.jdbc.Driver");
     Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/jewellery", "root", "admin");
     Statement st=con.createStatement();
     ResultSet rs=st.executeQuery("select userName,password from userlogin");
     while(rs.next())
         {
         String username=rs.getString(1);
         String password=rs.getString(2);
         if(user.equals(username) && pass.equals(password))
             {
			  session.setAttribute("username",username);
			  response.sendRedirect("accounts.htm");
             %>

			
         <%}
         else
         out.println("Login Failed,Please try Again");
        %>
        
         <%
     }
}catch(Exception e1)
{}

%>


</body>
</html>