<%@ page import="java.util.ArrayList" %>
<%--
  Created by IntelliJ IDEA.
  User: Echo01
  Date: 9/19/2017
  Time: 15:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
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
<% if(session.getAttribute("userLogged")== null)response.sendRedirect("index.jsp");
    String contentPath = request.getContextPath();
    request.setAttribute("contentPath",contentPath);
%>

<cst:baseTagfile title="${title}">
    <jsp:attribute name="base">
        ${errorMessage}
        <h3>Your Files:</h3>
        <c:forEach items="${urlData}" var="list">
                <p>Item: &nbsp;
                    <a href="${contentPath}/DownloadFile?fileName=${list}">
                        <c:out value="${list}"/></a></p>
            </c:forEach>
    </jsp:attribute>
</cst:baseTagfile>