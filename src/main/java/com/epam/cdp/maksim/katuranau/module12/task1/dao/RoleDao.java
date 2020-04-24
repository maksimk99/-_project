package com.epam.cdp.maksim.katuranau.module12.task1.dao;

import com.epam.cdp.maksim.katuranau.module12.task1.model.Role;
import com.epam.cdp.maksim.katuranau.module12.task1.model.RoleDto;

import java.util.List;

/**
 * The interface Role dao.
 */
public interface RoleDao {

    /**
     * Gets roles.
     *
     * @return the roles
     */
    List<Role> getRoles();

    /**
     * Create user role boolean.
     *
     * @param roles the roles
     * @return the boolean
     */
    Boolean createUserRole(List<RoleDto> roles);
}
