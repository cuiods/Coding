package edu.nju.exam.controller;

import edu.nju.exam.dao.ScoreDao;
import edu.nju.exam.dao.StudentDao;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "loginServlet",urlPatterns = "/login")
public class LoginServlet extends HttpServlet{

    private ScoreDao scoreDao;

    private StudentDao studentDao;

    @Override
    public void init() {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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
