package edu.nju.exam.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import edu.nju.exam.business.ScoreBean;
import edu.nju.exam.entity.StudentEntity;
import edu.nju.exam.service.ScoreService;
import edu.nju.exam.service.StudentService;
import edu.nju.exam.util.SessionCounter;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;

import javax.annotation.Resource;
import java.util.Map;

/**
 * login Action
 */
@Namespace("/")
@ResultPath(value = "/WEB-INF/jsp/")
public class LoginAction extends ActionSupport{

    @Resource
    private ScoreService scoreService;

    @Resource
    private StudentService studentService;

    @Action(value = "login", results = {
            @Result(name = "success",location = "score.jsp"),
            @Result(name = "none",location = "404.jsp"),
            @Result(name = "error",location = "418.jsp"),
            @Result(name = "login",location = "login.jsp")
    })
    public String execute() {
        String method = ServletActionContext.getRequest().getMethod();
        if(method.equals("GET")) return LOGIN;
        ActionContext context = ActionContext.getContext();
        Map parameters = context.getParameters();
        String usernameArray[] = (String[]) parameters.get("username");
        String passwordArray[] = (String[]) parameters.get("password");
        String username = usernameArray[0];
        String password = passwordArray[0];
        StudentEntity studentEntity = studentService.find(username);
        if (studentEntity == null) return NONE;
        else if (!studentService.verify(username,password)) return ERROR;
        SessionCounter.login();
        context.getSession().put("sid",studentEntity.getSid());
        context.getSession().put("username",studentEntity.getSname());
        ScoreBean scoreBean = new ScoreBean();
        scoreBean.setScoreEntities(scoreService.findList(studentEntity.getSid()));
        ServletActionContext.getRequest().setAttribute("scoreList",scoreBean);
        return SUCCESS;
    }

}
