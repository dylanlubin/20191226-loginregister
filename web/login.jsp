<%--
  Created by IntelliJ IDEA.
  User: ShenMouMou
  Date: 2019/12/20
  Time: 9:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<html>
<head>
    <title>login</title>
    <style>
        body{
            text-align: center;
        }

    </style>
</head>
<body>
        <%
            String username="";
            String password="";
            Cookie[] cookies = request.getCookies();
            if(cookies!=null){
                for (Cookie cookie : cookies) {
                    if(cookie.getName().equals("username")){
                      username = cookie.getValue();
                    }
                    if (cookie.getName().equals("password")) {
                        password = cookie.getValue();
                    }
                }
            }
        %>
        <h1>用户登录</h1>
        <form action="${pageContext.request.contextPath}/login" method="post" onsubmit="return true">

           用户名： <input type="text" name="username" placeholder="请输入用户名" value="<%=username%>"/><br>
           密码： <input type="password" name="password" placeholder="请输入密码" value="<%=password%>"/><br>
            记住密码：<input type="radio" name="rember" value="7" />记住一周
            <input type="radio" name="rember" value="30" />记住一个月
            <input type="radio" name="rember" value="0" />重置
            <br>
            <img src="${pageContext.request.contextPath}/code" id="img" onclick="changeImg()"/> <br>
            验证码：<input type="text" name="checkcode"><br>
                <a href=" #" onclick="changeImg()">看不清，换一张</a>
            <br>
            <input type="submit" value="登录">
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
