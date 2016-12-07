package edu.nju.exam;

import com.sun.deploy.net.HttpRequest;
import com.sun.deploy.net.HttpResponse;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

@WebServlet("/login.user")
public class Login extends HttpServlet{

    private Context context = null;
    private DataSource dataSource = null;

    @Override
    public void init() {
        try {
            context = new InitialContext();
            dataSource = (DataSource) context.lookup("java:comp/env/jdbc/examweb");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        //Database variables declaration
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSetUser;
        ResultSet resultSetScore;
        try {
            //get connection
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT * FROM student WHERE sname=? ");
            String uname = request.getParameter("username");
            statement.setString(1,uname);
            resultSetUser = statement.executeQuery();
            //if find user
            if (resultSetUser != null && resultSetUser.next()) {
                String psw = resultSetUser.getString("password");
                String inputpsw = request.getParameter("password");
                //if correct password
                if (psw.equals(inputpsw)) {
                    HttpSession session = request.getSession();
                    session.setAttribute("username",uname);
                    session.setAttribute("sid",resultSetUser.getInt("sid"));
                } else {//error password
                    error_418(request,response);
                }
            } else {//cannot find user
                error_404(request,response);
            }
            //get scores
            statement = connection.prepareStatement("SELECT course.cid AS courseId,course.cname AS cname,score.score AS score " +
                    "FROM student " +
                    "JOIN score ON student.sid=score.sid " +
                    "JOIN course ON score.cid=course.cid " +
                    "WHERE student.sname=? ");
            statement.setString(1,uname);
            resultSetScore = statement.executeQuery();
            //output scores
            PrintWriter writer = response.getWriter();
            HttpSession session = request.getSession();
            String username = (String) session.getAttribute("username");
            writer.println("<h1>Hi!"+username+"!</h1>");
            writer.println("<h2>Here is your scores.</h2>");
            writer.println("<table border=\"1\">");
            writer.println("<tr>\n" +
                    "<th>Course</th>\n" +
                    "<th>Score</th>\n" +
                    "</tr>");
            ArrayList<String> noScoreCourses = new ArrayList<String>();
            while (resultSetScore.next()) {
                String color = "black";
                int score = resultSetScore.getInt("score");
                if (score<60) {
                    color="red";
                }
                String scoreStr = resultSetScore.getString("score");
                if (scoreStr==null || scoreStr.equals("null")) {
                    scoreStr = "未参加考试";
                    noScoreCourses.add(resultSetScore.getString("cname"));
                }
                writer.println("<tr>\n" +
                        "<td>"+resultSetScore.getString("cname")+"</td>\n" +
                        "<td style=\"color:"+color+";\">"+scoreStr+"</td>\n" +
                        "</tr>");
            }
            writer.println("</table>");
            if (noScoreCourses.size()>0) {
                writer.println("<h2 style=\"color:yellow;\">WARNING:</h2>");
                for (String noScore: noScoreCourses) {
                    writer.println("<p>"+noScore+" 没有参加考试! </p>");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void error_500(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/html/error/500.html");
        if (dispatcher!=null) {
            dispatcher.forward(request,response);
        }
    }

    private void error_404(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/html/error/404.html");
        if (dispatcher!=null) {
            dispatcher.forward(request,response);
        }
    }

    private void error_418(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/html/error/418.html");
        if (dispatcher!=null) {
            dispatcher.forward(request,response);
        }
    }

}
