package com.nowcoder.model;

import org.springframework.stereotype.Component;

/**
 * @Author: AnNing
 * @Description:
 * @Date: Create in 16:58 2019/4/9
 */
@Component
public class HostHolder {
    private static ThreadLocal<User> users = new ThreadLocal<User>();

    public User getUser() {
        return users.get();
    }

    public void setUser(User user) {
        users.set(user);
    }

    public void clear() {
        users.remove();;
    }
}
