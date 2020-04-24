package com.epam.cdp.maksim.katuranau.module12.task1.dao.jdbc.mapper;

import com.epam.cdp.maksim.katuranau.module12.task1.model.Role;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RoleRowMapper implements RowMapper<Role> {

    private static final String ROLE_ID = "role_id";
    private static final String ROLE_NAME = "name";

    @Override
    public Role mapRow(final ResultSet resultSet, final int i) throws SQLException {
        return new Role(resultSet.getInt(ROLE_ID), resultSet.getString(ROLE_NAME));
    }
}
