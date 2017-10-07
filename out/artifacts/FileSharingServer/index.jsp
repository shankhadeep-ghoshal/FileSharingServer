<%--
  Created by IntelliJ IDEA.
  User: Echo01
  Date: 9/14/2017
  Time: 22:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
  ${message}
  <form method="post" action="Login">
      <input type="text" name="user_name">
      <input type="password" name="pass">
      <input type="submit" value="Login">
  </form>
  <div align="right">
      <a href="SignUp.jsp">Click to sign up</a>
  </div>
  </body>
</html>
