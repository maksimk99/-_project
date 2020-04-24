package com.epam.cdp.maksim.katuranau.module12.task1.dao;

import com.epam.cdp.maksim.katuranau.module12.task1.model.User;
import com.epam.cdp.maksim.katuranau.module12.task1.model.UserRegistrationDto;

/**
 * The interface User dao.
 */
public interface UserDao {

    /**
     * Create long.
     *
     * @param user the user
     * @return the long
     */
    Long create(UserRegistrationDto user);

    /**
     * Gets user id by login and password.
     *
     * @param login the user login
     * @return the user id by login and password
     */
    Long getUserIdByLogin(String login);

    /**
     * Gets user by login.
     *
     * @param login the login
     * @return the user by login
     */
    User getUserByLogin(String login);

    /**
     * Update boolean.
     *
     * @param user the user
     * @return the boolean
     */
    Boolean update(User user);
}
