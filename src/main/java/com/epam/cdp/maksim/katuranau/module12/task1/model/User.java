package com.epam.cdp.maksim.katuranau.module12.task1.model;

import java.util.List;

public class User {

    private Long id;
    private String login;
    private String password;
    private List<Role> roleList;

    public Long getId() {
        return id;
    }

    public User setId(final Long id) {
        this.id = id;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public User setLogin(final String login) {
        this.login = login;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(final String password) {
        this.password = password;
        return this;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public User setRoleList(final List<Role> roleList) {
        this.roleList = roleList;
        return this;
    }
}
