package com.lggyx.context;

public class BaseContext {

    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();

    public static void setCurrentAccount(String account) {
        THREAD_LOCAL.set(account);
    }

    public static String getCurrentAccount() {
        return THREAD_LOCAL.get();
    }

    public static void removeCurrentAccount() {
        THREAD_LOCAL.remove();
    }

    public static String getCurrentToken() {
        return THREAD_LOCAL.get();
    }

}
