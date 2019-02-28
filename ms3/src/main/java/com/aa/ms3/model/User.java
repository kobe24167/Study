package com.aa.ms3.model;

import java.util.Date;

import lombok.Data;

@Data
public class User {
    private Integer id;

    private String password;

    private String mobile;

    private String mail;

    private String name;

    private String nickName;

    private String salt;

    private String openId;

    private String unionId;

    private Date addTime;

    private Date lastTime;
}