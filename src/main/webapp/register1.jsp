<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 2017/6/17
<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 2017/6/15
  Time: 21:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册1</title>
</head>
<body>
<h1>注册1</h1>
<hr>
<form action="student" method="post">
    <input type="hidden" name="action" value="register1">
    <input type="text" name="name" placeholder="用户名">
    <input type="password" name="password" placeholder="密码">
    <input type="submit" value="注册">
</form>
${requestScope.message}
</body>
</html>

