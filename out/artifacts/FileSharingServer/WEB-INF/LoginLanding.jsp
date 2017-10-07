<%@ page import="java.util.ArrayList" %>
<%@ page import="DAOPackage.FileDAO" %><%--
  Created by IntelliJ IDEA.
  User: Echo01
  Date: 9/19/2017
  Time: 15:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="cst" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<form method="post" action="<%=request.getContextPath()%>/Logout">
    <input type="submit" value="Logout">
</form>
<form action="<%=request.getContextPath()%>/Upload" method="post" enctype="multipart/form-data">
    <input type="file" name = "file" value="Select Files">&nbsp;
    <input type="submit" value="Upload">
</form>
<% if(session.getAttribute("userLogged")== null)response.sendRedirect("index.jsp"); %>

<cst:baseTagfile title="${title}">
    <jsp:attribute name="base">
        ${errorMessage}
        <h3>Your Files:</h3>
            <c:forEach items="${urlDat}" var="list">
                <p><a href="#">Item : <c:out value="${list.fileLocation}"/></a></p>
            </c:forEach>
    </jsp:attribute>
</cst:baseTagfile>