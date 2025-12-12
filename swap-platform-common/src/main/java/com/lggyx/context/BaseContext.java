package com.lggyx.context;

/**
 *
 */
public class BaseContext {

    public static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void setCurrentAccount(String account) {
        threadLocal.set(account);
    }
    public static void setCurrentToken(String token) {
        threadLocal.set(token);
    }

    public static String getCurrentAccount() {
        return threadLocal.get();
    }
    public static String getCurrentToken() {
        return threadLocal.get();
    }

    public static void removeCurrentAccount() {
        threadLocal.remove();
    }
    public static void removeCurrentToken() {
        threadLocal.remove();
    }

}