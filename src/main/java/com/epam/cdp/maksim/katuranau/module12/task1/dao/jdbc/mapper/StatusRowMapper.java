package com.epam.cdp.maksim.katuranau.module12.task1.dao.jdbc.mapper;

import com.epam.cdp.maksim.katuranau.module12.task1.model.Status;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class StatusRowMapper implements RowMapper<Status> {

    private static final String STATUS_ID = "status_id";
    private static final String STATUS_NAME = "status_name";

    @Override
    public Status mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Status(resultSet.getLong(STATUS_ID), resultSet.getString(STATUS_NAME));
    }
}
