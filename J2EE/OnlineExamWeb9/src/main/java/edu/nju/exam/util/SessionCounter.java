package edu.nju.exam.util;

/**
 * count user of different type
 */
public class SessionCounter {
    private static int sumUser = 0;
    private static int visitor = 0;
    private static int login = 0;

    public static int getSumUser() {
        return sumUser;
    }

    public static int getVisitor() {
        return visitor;
    }

    public static int getLogin() {
        return login;
    }

    public static void create() {
        synchronized (SessionCounter.class) {
            sumUser++;
            visitor++;
        }
    }

    public static void login() {
        synchronized (SessionCounter.class) {
            visitor--;
            login++;
        }
    }

    public static void userLogout() {
        synchronized (SessionCounter.class) {
            sumUser--;
            login--;
        }
    }

    public static void visitorOut() {
        synchronized (SessionCounter.class) {
            sumUser--;
            visitor--;
        }
    }
}
