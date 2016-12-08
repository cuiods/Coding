package edu.nju.exam;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * web session listener
 * used to count user number
 */
@WebListener("/*")
public class UserListenner implements HttpSessionListener{

    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        SessionCounter.create();
    }

    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        if (session.getAttribute("username")!=null) {
            SessionCounter.userLogout();
        } else {
            SessionCounter.visitorOut();
        }
    }
}
