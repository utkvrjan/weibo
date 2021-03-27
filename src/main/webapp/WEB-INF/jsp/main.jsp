<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>主页</title>

    <style>
        div{
            border: 1px solid black;
        }
        #message{
            width: 400px;
            height: 400px;
            position: fixed;
            left: 600px;
            top: 200px;
            display: none;
        }

        #message > div{
            float: left;
        }

        #friend{
            width: 98px;
            height: auto;
            background-color: aqua;
        }

        #showMessage{
            width: 298px;
            height: 100%;
            background-color: burlywood;
        }
    </style>

    <script src="${pageContext.request.contextPath}/js/jquery-1.11.2.js"></script>

    <script>
        function getAddress(){
            $.get("${pageContext.request.contextPath}/user/getAddress",{},function(data){
                $("#address").text("你来自:"+data);
            });
        }

        $(function(){
            getAddress();
        })
    </script>
</head>
<body background="${pageContext.request.contextPath}/2.jpeg">
<%--未登录的时候--%>
<c:if test="${CUR_USER == null}">
    <a style="float: right" href="${pageContext.request.contextPath}/user/reg">注册</a>/<a style="float: right" href="${pageContext.request.contextPath}/user/login">登录</a>
</c:if>
<%--已登录的情况--%>
<c:if test="${CUR_USER != null}">
    <div>
        <a href="${pageContext.request.contextPath}/user/update">
            <img width="50px" height="50px" src="${pageContext.request.contextPath}/files/photo/${CUR_USER.photo}" alt="">
        </a><br>
        <span>${CUR_USER.nickname}</span><br>
        <span>粉丝:${followCount}</span><span>关注:${aboutCount}</span><br>
        <span>上次登录时间:${CUR_USER==null?"你是第一次登录":CUR_USER.loginTime}</span>
        <a href="${pageContext.request.contextPath}/user/logoutDo">退出登录</a>
<%--        <input type="button" id="btn" value="私信">--%>
        <span id="address"></span>
    </div>
</c:if>

<%--搜索--%>
<%--<form action="${pageContext.request.contextPath}/user/search" method="get">--%>
<%--    <input type="text" name="keyWords">--%>
<%--    <input type="submit" value="搜索">--%>
<%--</form>--%>


<%--发微博--%>
<form action="${pageContext.request.contextPath}/weibo/sendWeibo" method="post" enctype="multipart/form-data">
    <textarea name="content"  cols="60" rows="3"></textarea><br>
    图片<input type="file" name="pic"><br>
<%--    音频/视频<input type="file" name="video"><br>--%>
    <input type="submit" value="发微博">
</form>


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
            <a href="${pageContext.request.contextPath}/weibo/unfabulous?wid=${weibo.wid}&url=/user/main">取消赞</a>
        </c:if>
        <c:if test="${weibo.fabulous == false}">
            <a href="${pageContext.request.contextPath}/weibo/fabulous?wid=${weibo.wid}&url=/user/main">赞</a>
        </c:if>

            <%--显示评论--%>
        <c:forEach items="${weibo.commentsList}" var="comments">
            <div style="margin-left:30px;border: 1px solid;margin-top: 5px;">
                <img src="${pageContext.request.contextPath}/files/photo/${comments.uid.photo}" width="50px" alt="">
                <span>&nbsp;&nbsp;${comments.uid.nickname}&nbsp;&nbsp;${comments.sendTime}</span><br>
                <c:if test="${comments.content != null}">
                    <span>${comments.content}</span><br><%--内容--%>
                </c:if>
            </div>
        </c:forEach>


            <%--评论--%>

        <form action="${pageContext.request.contextPath}/weibo/comments" method="post">
            <input type="hidden" name="wid" value="${weibo.wid}">
            <input type="hidden" name="url" value="/user/main">
            <textarea name="content" cols="60" rows="3" style="background-color: #ffd500;"></textarea><br>
            <input style="background-color: #ffd500;float: right" type="submit" value="评论">
        </form>

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
    <div id="friend"></div>
    <div id="showMessage"></div>
</div>

<script>
    /**
     * 显示和隐藏私信窗口
     */
    function showAndCloseMessage(){
        var message = $("#message");
        $("#message").toggle();
        if(message.css("display") == "block"){//如果是显示
            //查询一下哪些人发送过私信
            $.get("${pageContext.request.contextPath}/user/getFriends",{},function(data){
                data = JSON.parse(data);
                if(data.state == 2){//未登录的判断
                    alert(data.msg);
                    //跳转到登录界面
                    window.location.href="${pageContext.request.contextPath}/user/login";
                } else if(data.state == 0){
                    var friend = $("#friend");
                    friend.empty();
                    //循环发送者
                    $.each(data.data,function(index,ele){
                        var div = $("<div></div>");
                        friend.append(div);
                        div.text(ele.nickname);//显示昵称
                        div.on("click",{"uid":ele.uid },function(event){
                            //添加事件，点击某一个人，右边显示这个人的私信
                            var uid = event.data.uid;
                            //发送请求出去 获取私信的内容
                            $.get("${pageContext.request.contextPath}/user/getMessage",{"uid":uid},function(data){
                                data = JSON.parse(data);
                                if(data.state == 2){//未登录的判断
                                    alert(data.msg);
                                    //跳转到登录界面
                                    window.location.href="${pageContext.request.contextPath}/user/login";
                                } else if(data.state == 0){
                                    var showmessage = $("#showMessage");
                                    showmessage.empty();
                                    $.each(data.data,function(index,ele){
                                        var str = "<span>"+ele.sender.nickname+"</span>";
                                        str += "&nbsp;&nbsp;&nbsp;&nbsp;<span>"+ele.sendTime+"</span><br>";
                                        str += "<p>"+ele.mcontent+"</p>";
                                        showmessage.append(str);
                                    })
                                }
                            });

                        });
                    });
                }
            });
        }
    }

    $("#btn").click(function(){
        showAndCloseMessage();
    });



</script>
</body>
</html>
