<%@ page import="edu.nju.exam.util.SessionCounter" %><%--
  Use custom taglib to show scores
  User: cuihao
  Date: 2016/12/9
  Time: 11:28
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/scoreInfo" prefix="score"%>
<html>
<head>
    <title>Score Info</title>
</head>
<body>
<score:checkSession/>
<h1>欢迎，${username}! 点击这里 <a href="<%=request.getContextPath()+"/logout.do"%>">退出登录</a>.</h1>
<h2>你的课程分数是：</h2>
<score:showScoreInfo scoreEntities="${scoreList.scoreEntities}"/>
<h2 style="color:blue;">统计信息:</h2>
<p>在线用户：<%=SessionCounter.getSumUser()%></p>
<p>登陆用户：<%=SessionCounter.getLogin()%></p>
<p>游客：<%=SessionCounter.getVisitor()%></p>
</body>
</html>
