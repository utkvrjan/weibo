<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>账号注册</title>
</head>
<body>
<%
    String cnt = "100";
    pageContext.setAttribute("cnt",cnt);
%>
${cnt+100}

<form action="${pageContext.request.contextPath}/user/regDo" method="post">
    <span>账号</span><input type="text" name="username" /><br>
    <span>密码</span><input type="password" name="password" /><br>
    <span>邮箱</span><input type="email" name="email" /><br>
    <span>昵称</span><input type="text" name="nickname" /><br>
    <span>性别</span><input type="radio" name="sex" value="男" />男<input type="radio" name="sex" value="女"/>女<br>
    <input type="submit" value="注册" />
</form>
</body>
</html>