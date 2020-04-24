package com.epam.cdp.maksim.katuranau.module12.task1.dao.jdbc;

import com.epam.cdp.maksim.katuranau.module12.task1.dao.RoleDao;
import com.epam.cdp.maksim.katuranau.module12.task1.dao.jdbc.mapper.RoleRowMapper;
import com.epam.cdp.maksim.katuranau.module12.task1.model.Role;
import com.epam.cdp.maksim.katuranau.module12.task1.model.RoleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {

    @Value("${role.select}")
    private String ROLES_LIST_GET_SQL;
    @Value("${role.insertUserRole}")
    private String ROLE_ADD_TO_USER_SQL;

    private final RoleRowMapper roleRowMapper;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public RoleDaoImpl(
            final RoleRowMapper roleRowMapper,
            final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.roleRowMapper = roleRowMapper;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Role> getRoles() {
        return namedParameterJdbcTemplate.query(ROLES_LIST_GET_SQL, roleRowMapper);
    }

    @Override
    public Boolean createUserRole(final List<RoleDto> roles) {
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(roles);
        return namedParameterJdbcTemplate.batchUpdate(ROLE_ADD_TO_USER_SQL, batch).length == roles.size();
    }
}
