package com.epam.cdp.maksim.katuranau.module12.task1.dao.jdbc.mapper;

import com.epam.cdp.maksim.katuranau.module12.task1.model.Role;
import com.epam.cdp.maksim.katuranau.module12.task1.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class UserRowMapper implements RowMapper<User> {

    private static final String USER_ID = "user_id";
    private static final String USER_LOGIN = "login";
    private static final String USER_PASSWORD = "password";

    private RoleRowMapper roleRowMapper;

    @Autowired
    public UserRowMapper(RoleRowMapper roleRowMapper) {
        this.roleRowMapper = roleRowMapper;
    }

    @Override
    public User mapRow(final ResultSet resultSet, final int i) throws SQLException {
        User user = new User().setId(resultSet.getLong(USER_ID))
                .setLogin(resultSet.getString(USER_LOGIN))
                .setPassword(resultSet.getString(USER_PASSWORD));
        List<Role> roleList = new ArrayList<>();
        Role role;
        do {
            role = roleRowMapper.mapRow(resultSet, i);
            if (Objects.nonNull(role.getName())) {
                roleList.add(role);
            }
        } while (resultSet.next());
        user.setRoleList(roleList);
        return user;
    }
}
