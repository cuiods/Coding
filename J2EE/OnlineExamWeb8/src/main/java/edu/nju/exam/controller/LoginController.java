package edu.nju.exam.controller;

import edu.nju.exam.business.ScoreBean;
import edu.nju.exam.entity.StudentEntity;
import edu.nju.exam.service.ScoreService;
import edu.nju.exam.service.StudentService;
import edu.nju.exam.util.SessionCounter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Login controller
 * @author cuihao
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Resource
    private StudentService studentService;

    @Resource
    private ScoreService scoreService;

    @RequestMapping(value = "",method = RequestMethod.POST)
    public String doPost(@RequestParam String username, @RequestParam String password, HttpServletRequest request) {
        StudentEntity studentEntity = studentService.find(username);
        if (studentEntity==null) {
            return error_404();
        } else if (!studentService.verify(username,password)) {
            return error_418();
        }
        HttpSession session = request.getSession();
        SessionCounter.login();
        session.setAttribute("sid",studentEntity.getSid());
        session.setAttribute("username",studentEntity.getSname());
        return showScoreList(studentEntity.getSid(), request);
    }

    @RequestMapping(value = "",method = RequestMethod.GET)
    public String doGet() {
        return "login";
    }

    private String showScoreList(int studentId, HttpServletRequest request) {
        ScoreBean scoreBean = new ScoreBean();
        scoreBean.setScoreEntities(scoreService.findList(studentId));
        request.setAttribute("scoreList",scoreBean);
        return "score";
    }

    private String error_404() {
        return "404";
    }

    private String error_418() {
        return "418";
    }
}
