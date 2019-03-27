package com.nowcoder.model;

/**
 * @Author: AnNing
 * @Description:
 * @Date: Create in 10:50 2019/3/27
 */
public class User {
    private  Integer Id;
    private  String  Name;
    private  String Password;
    private  String Salt;
    private String HeadUrl;

    public User(String name) {
        Name = name;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getSalt() {
        return Salt;
    }

    public void setSalt(String salt) {
        Salt = salt;
    }

    public String getHeadUrl() {
        return HeadUrl;
    }

    public void setHeadUrl(String headUrl) {
        HeadUrl = headUrl;
    }
}
