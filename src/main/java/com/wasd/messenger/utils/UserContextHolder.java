package com.wasd.messenger.utils;

import com.wasd.messenger.entity.User;

public class UserContextHolder {

    private static final ThreadLocal<User> contextHolder = new ThreadLocal<>();

    private UserContextHolder() {
    }

    public static void setUser(User user) {
        contextHolder.set(user);
    }

    public static User getUser() {
        return contextHolder.get();
    }

    public static void clear() {
        contextHolder.remove();
    }
}