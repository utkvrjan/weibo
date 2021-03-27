<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
    <style>
        form img:hover{
            cursor: pointer;
        }
    </style>
    <script>
        function changeImg(obj){
            obj.src="${pageContext.request.contextPath}/user/validate?"+Math.random();
        }
    </script>
</head>
<body>

<form action="${pageContext.request.contextPath}/user/loginDo" method="post">
    <h3>登录界面</h3>
    <span>账号</span> <input type="text" name="username"><br>
    <span>密码</span> <input type="password" name="password"><br>
    <span>验证码</span> <input type="text" name="val"><img src="${pageContext.request.contextPath}/user/validate" onclick="changeImg(this)" alt=""><br>
    <input type="submit" value="登录">
</form>
</body>
</html>
