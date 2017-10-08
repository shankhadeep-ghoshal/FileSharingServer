<%--suppress ALL --%>
<%--suppress ALL --%>
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
    <title>File Sharing Server</title>
  </head>
  <body>
  <h1>Your personal file storage and sharing hub</h1>
  ${message}
  <form method="post" action="Login">
      <label>
          <input type="text" name="user_name">
      </label>
      <label>
          <input type="password" name="pass">
      </label>
      <input type="submit" value="Login">
  </form>
  <div align="right">
      <a href="SignUp.jsp">Click to sign up</a>
  </div>
  </body>
</html>
