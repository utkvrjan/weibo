<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改个人信息</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/user/updateDo" method="post" enctype="multipart/form-data">
    <input type="hidden" name="uid" value="${CUR_USER.uid}">
    <img src="${pageContext.request.contextPath}/files/photo/${CUR_USER.photo}" width="50px" height="50px" alt=""><br>
    <input type="file" name="photo"><br>
    <span>账号</span><input type="text" name="username" value="${CUR_USER.username}" readonly="readonly"><br>
    <span>密码</span><input type="password" name="password"><br>
    <span>邮箱</span><input type="email" name="email"  value="${CUR_USER.email}"><br>
    <span>昵称</span><input type="text" name="nickname"  value="${CUR_USER.nickname}"><br>
    <span>性别</span><input type="radio" name="sex" value="男"  ${CUR_USER.sex=="男"?"checked='checked'":""}>男<input type="radio" name="sex" value="女" ${CUR_USER.sex=="女"?"checked='checked'":""}>女<br>
    <input type="submit" value="修改">
</form>
</body>
</html>
