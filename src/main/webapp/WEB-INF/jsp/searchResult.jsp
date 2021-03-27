<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>搜索结果</title>
</head>
<body>
<c:if test="${fn:length(users) == 0}">
    <span>没有结果</span>
    <a href="${pageContext.request.contextPath}/user/main">返回主页</a>
</c:if>

<c:forEach items="${users}" var="user">
    <div style="border: 1px solid; margin-top: 5px">
        <a href="${pageContext.request.contextPath}/user/personInfo?uid=${user.uid}">
            <img src="${pageContext.request.contextPath}/files/photo/${user.photo}" width="50px" alt="">
            <span>&nbsp;&nbsp;${user.username}</span>
            <span>&nbsp;&nbsp;${user.nickname}</span>
        </a>
    </div>
</c:forEach>

</body>
</html>
