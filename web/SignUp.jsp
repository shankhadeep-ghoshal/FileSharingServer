<%--suppress ALL --%>
<%--
  Created by IntelliJ IDEA.
  User: Echo01
  Date: 9/15/2017
  Time: 16:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>SignUp</title>
</head>
<body>
    <div>
        ${msg}
        ${msg2}
            <form method="post" action="ServletAuth">
            <label for="passID">UserName: </label><input autocomplete="off" id="passID" type="text"
                                                         name="user_name" oninput="fn()"
                                                          required><br>
            <label for="confPass">Password: </label><input id="confPass" autocomplete="off" type="password" name="pass"
                                                           oninput="fn()"
                                                           required><br>
            <label for="confPass2">Confirm Password: </label><input id="confPass2" autocomplete="off" type="password" name="passConf" oninput="fn()" required><br>
            <input id="btn" type="submit" value="Sign Up" disabled>
        </form>
        <script>
            function fn(){
                var x = document.getElementById("confPass2").value;
                var y = document.getElementById("confPass").value;
                var z = document.getElementById("passID").value;
                document.getElementById("btn").disabled = !(z.length > 7 && x.length > 7 && y.length > 7 && x === y);
            }
        </script>
    </div>
</body>
</html>
