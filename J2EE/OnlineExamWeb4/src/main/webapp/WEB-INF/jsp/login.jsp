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
    <h2>Welcome! Please login to view your scores.</h2>
    <form action="/login" method="post">
        <fieldset style="max-width: 400px">
            <legend>name and password</legend>
            <p>
                <label for="username">Username:</label>
                <input type="text" name="username" id="username" required="" />
            </p>
            <p>
                <label for="password">Password:</label>
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