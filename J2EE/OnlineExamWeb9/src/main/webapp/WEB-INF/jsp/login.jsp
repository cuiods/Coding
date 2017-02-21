<%--
  Created by IntelliJ IDEA.
  User: cuihao
  Date: 2016/12/9
  Time: 11:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<div>
    <h2>欢迎！请登录后查看考试成绩。</h2>
    <form action="/login.action" method="post">
        <fieldset style="max-width: 400px">
            <legend>用户名和密码</legend>
            <p>
                <label for="username">用户名:</label>
                <input type="text" name="username" id="username" required="" />
            </p>
            <p>
                <label for="password">密码:</label>
                <input type="password" name="password" id="password" required=""/>
            </p>
            <p style="text-align: right">
                <input type="submit" name="submit"/>
            </p>
        </fieldset>
    </form>
</div>
</body>
</html>