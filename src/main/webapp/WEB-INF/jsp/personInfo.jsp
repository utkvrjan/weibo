<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>个人信息</title>
    <style>
        #message{
            display: none;
            width: 300px;
            height: 200px;
            position: fixed;
            left: 300px;
            top: 200px;
        }
    </style>
    <script src="${pageContext.request.contextPath}/js/jquery-1.11.2.js"></script>

</head>
<body>
<%--个人信息--%>
<div>
    <img width="50px" height="50px" src="${pageContext.request.contextPath}/files/photo/${user.photo}" alt="">
    <br>
    <span>${user.nickname}</span><br>
    <span>粉丝:${followCount}</span><span>关注:${aboutCount}</span><br>
    <c:if test="${isFollow == false}">
        <a href="${pageContext.request.contextPath}/user/follow?uid=${user.uid}">关注</a>
    </c:if>
    <c:if test="${isFollow == true}">
        <a href="${pageContext.request.contextPath}/user/unfollow?uid=${user.uid}">取消关注</a>
    </c:if>
    <input type="button" id="btn" value="私信">
</div>


<%--显示微博--%>

<c:forEach items="${pager.datas}" var="weibo">
    <div style="border: 1px solid;margin-top: 5px;">
            <%--发送者的信息--%>
        <img src="${pageContext.request.contextPath}/files/photo/${weibo.sender.photo}" width="50px" alt="">
        <span>&nbsp;&nbsp;${weibo.sender.nickname}&nbsp;&nbsp;${weibo.sendTime}</span><br>
        <c:if test="${weibo.content != null}">
            <span>${weibo.content}</span><br><%--内容--%>
        </c:if>
            <%--图片--%>
        <c:if test="${weibo.pic != null}">
            <img width="150px" src="${pageContext.request.contextPath}/files/images/${weibo.pic}" alt=""><br>
        </c:if>
            <%--视频/音频--%>
        <c:if test="${weibo.video != null}">
            <video width="200px" controls="controls" src="${pageContext.request.contextPath}/files/video/${weibo.video}"></video>
            <br>
        </c:if>
            <%--点赞--%>
        <c:if test="${weibo.fabulous == true}">
            <a href="${pageContext.request.contextPath}/weibo/unfabulous?wid=${weibo.wid}&url=/user/personInfo&uid=${user.uid}">取消赞</a>
        </c:if>
        <c:if test="${weibo.fabulous == false}">
            <a href="${pageContext.request.contextPath}/weibo/fabulous?wid=${weibo.wid}&url=/user/personInfo&uid=${user.uid}">赞</a>
        </c:if>
    </div>

</c:forEach>

<%--页码--%>
<c:if test="${pager.pageNow > 1}">
    <a href="${pageContext.request.contextPath}/user/main?pageNow=${pager.pageNow -1 }">上一页</a>
</c:if>

<c:forEach var="i" begin="1" end="${pager.pageCount}">
    <a href="${pageContext.request.contextPath}/user/main?pageNow=${i}">${i}</a>
</c:forEach>


<c:if test="${pager.pageNow < pager.pageCount}">
    <a href="${pageContext.request.contextPath}/user/main?pageNow=${pager.pageNow + 1 }">下一页</a>
</c:if>

<div id="message">
    <textarea name="" id="msg" cols="30" rows="10"></textarea>
    <input type="button" id="send" value="发送私信">
</div>

<script>
    /**
     * 显示和隐藏私信窗口
     */
    function showAndCloseMessage(){
        $("#message").toggle();
    }

    function sendMessage(){
        $.post("${pageContext.request.contextPath}/user/sendMessage",{"mcontent":$("#msg").val(),"uid":"${user.uid}"},function(data){
            //将json字符串转换成json对象
            data = JSON.parse(data);//js中有内置的JSON 直接将字符串转换成json对象
            if(data.state == 2){//未登录的判断
                alert(data.msg);
                //跳转到登录界面
                window.location.href="${pageContext.request.contextPath}/user/login";
            } else if(data.state == 0){
                alert("私信成功");
                showAndCloseMessage();
            } else if(data.state == 1){
                alert("私信失败");
            }
        })
    }

    $("#btn").click(function(){
        showAndCloseMessage();
    });

    $("#send").click(function(){
        sendMessage();
    });

</script>
</body>
</html>
