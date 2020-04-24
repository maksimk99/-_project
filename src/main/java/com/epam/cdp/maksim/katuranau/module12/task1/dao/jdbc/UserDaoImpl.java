package com.epam.cdp.maksim.katuranau.module12.task1.dao.jdbc;

import com.epam.cdp.maksim.katuranau.module12.task1.dao.UserDao;
import com.epam.cdp.maksim.katuranau.module12.task1.dao.jdbc.mapper.UserRowMapper;
import com.epam.cdp.maksim.katuranau.module12.task1.model.User;
import com.epam.cdp.maksim.katuranau.module12.task1.model.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

    @Value("${user.insert}")
    private String USER_INSERT_SQL;
    @Value("${user.selectIdByLogin}")
    private String USER_ID_SELECT_BY_LOGIN_SQL;
    @Value("${user.selectByLogin}")
    private String USER__SELECT_BY_LOGIN_SQL;
    @Value("${user.update}")
    private String USER_UPDATE_SQL;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final UserRowMapper userRowMapper;

    /**
     * Instantiates a new User dao.
     *
     * @param namedParameterJdbcTemplate the named parameter jdbc template
     * @param userRowMapper              the user row mapper
     */
    @Autowired
    public UserDaoImpl(
            final NamedParameterJdbcTemplate namedParameterJdbcTemplate,
            final UserRowMapper userRowMapper) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.userRowMapper = userRowMapper;
    }

    @Override
    public Long create(final UserRegistrationDto user) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("login", user.getLogin());
        mapSqlParameterSource.addValue("password", user.getPassword());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(USER_INSERT_SQL, mapSqlParameterSource, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public Long getUserIdByLogin(final String login) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("login", login);
        return namedParameterJdbcTemplate.queryForObject(USER_ID_SELECT_BY_LOGIN_SQL, mapSqlParameterSource,
                Long.class);
    }

    /**
     * Gets user by login.
     *
     * @param login the login
     * @return the user by login
     */
    @Override
    public User getUserByLogin(final String login) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("login", login);
        try {
            return namedParameterJdbcTemplate.queryForObject(USER__SELECT_BY_LOGIN_SQL, mapSqlParameterSource,
                    userRowMapper);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public Boolean update(final User user) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", user.getId());
        mapSqlParameterSource.addValue("login", user.getLogin());
        mapSqlParameterSource.addValue("password", user.getPassword());
        return namedParameterJdbcTemplate.update(USER_UPDATE_SQL, mapSqlParameterSource) == 1;
    }
}
