package edu.nju.exam.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;

/**
 * logout action
 */
@Namespace("/")
@ResultPath(value = "/WEB-INF/jsp/")
@Result(name = "success",location = "login.jsp")
public class LogoutAction extends ActionSupport{

    public String execute() {
        ActionContext.getContext().getSession().clear();
        return SUCCESS;
    }
}
