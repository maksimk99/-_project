package com.epam.cdp.maksim.katuranau.module12.task1.model;

import java.util.Objects;

public class UserAuthorizationDto {

    private String login;
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAuthorizationDto)) return false;
        UserAuthorizationDto that = (UserAuthorizationDto) o;
        return Objects.equals(login, that.login) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password);
    }
}
