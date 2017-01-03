package edu.nju.exam.taglib;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * auth verify
 * @author cuihao
 */
public class AuthTag extends TagSupport{
    @Override
    public int doStartTag() throws JspException {
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        HttpSession session = pageContext.getSession();
        if (session!=null && session.getAttribute("username")!= null) {
            return EVAL_PAGE;
        }
        HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
        try {
            response.sendRedirect("/login");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_PAGE;
    }

    @Override
    public void release() {
        super.release();
    }

}
