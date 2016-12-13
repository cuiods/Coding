package edu.nju.exam.controller;

import edu.nju.exam.business.ScoreBean;
import edu.nju.exam.dao.ScoreDao;
import edu.nju.exam.dao.StudentDao;
import edu.nju.exam.factory.DaoFactory;
import edu.nju.exam.factory.DaoFactoryImpl;
import edu.nju.exam.model.StudentEntity;
import edu.nju.exam.util.SessionCounter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "loginServlet",urlPatterns = "/login")
public class LoginServlet extends HttpServlet{

    private DaoFactory daoFactory;

    private StudentDao studentDao;

    private ScoreDao scoreDao;

    @Override
    public void init() {
        daoFactory = DaoFactoryImpl.getInstance();
        studentDao = daoFactory.getStudentDao();
        scoreDao = daoFactory.getScoreDao();
        assert studentDao!=null && scoreDao!=null;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        StudentEntity studentEntity = studentDao.find(username);
        if (studentEntity==null) {
            error_404(request, response);
        } else if (!studentDao.verify(username,password)) {
            error_418(request, response);
        }
        HttpSession session = request.getSession();
        SessionCounter.login();
        session.setAttribute("sid",studentEntity.getStudentId());
        session.setAttribute("username",studentEntity.getStudentName());
        showScoreList(studentEntity.getStudentId(), request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getSession(true);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
        if (dispatcher!=null) {
            dispatcher.forward(request,response);
        }
    }

    private void showScoreList(int studentId, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        ScoreBean scoreBean = new ScoreBean();
        scoreBean.setScoreEntities(scoreDao.findList(studentId));
        request.setAttribute("scoreList",scoreBean);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/score.jsp");
        if (dispatcher != null) {
            dispatcher.forward(request,response);
        }
    }

//    private void error_500(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/html/error/500.html");
//        if (dispatcher!=null) {
//            dispatcher.forward(request,response);
//        }
//    }

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
