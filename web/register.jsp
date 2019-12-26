<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/12/23
  Time: 14:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>register</title>
    <style>
        body{
            text-align: center;
        }
    </style>
</head>
<body>
<h1>用户注册</h1>
<form action="${pageContext.request.contextPath}/register" method="post" onsubmit="return true">
    用户名： <input type="text" name="username" placeholder="请输入用户名" /><br>
    密码： <input type="password" name="password" placeholder="请输入密码" /><br>
    <br>
    <img src="${pageContext.request.contextPath}/code" id="img" onclick="changeImg()"/> <br>
    验证码：<input type="text" name="checkcode"><br>
    <a href=" #" onclick="changeImg()">看不清，换一张</a>
    <br>
    <input type="submit" value="注册">
</form>

<div style="color: red">
    <%=request.getAttribute("msg")==null?"":request.getAttribute("msg")%>
    <%=request.getSession().getAttribute("msg") == null ? "" : request.getSession().getAttribute("msg")%>
</div>

<script>
    function changeImg() {
        document.getElementById("img").src="${pageContext.request.contextPath}/code?time="+new Date().getTime();
    }
</script>
</body>
</html>
