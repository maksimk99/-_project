package com.epam.cdp.maksim.katuranau.module12.task1.service.impl;

import com.epam.cdp.maksim.katuranau.module12.task1.dao.RoleDao;
import com.epam.cdp.maksim.katuranau.module12.task1.dao.UserDao;
import com.epam.cdp.maksim.katuranau.module12.task1.exception.security.UserAlreadyExistException;
import com.epam.cdp.maksim.katuranau.module12.task1.model.RoleDto;
import com.epam.cdp.maksim.katuranau.module12.task1.model.UserRegistrationDto;
import com.epam.cdp.maksim.katuranau.module12.task1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private RoleDao roleDao;
    private BCryptPasswordEncoder passwordEncoder;
    private UserDetailsService userDetailsService;
    private AuthenticationManager authenticationManager;
    private MessageSource messageSource;


    @Autowired
    public UserServiceImpl(final UserDao userDao, RoleDao roleDao, final BCryptPasswordEncoder passwordEncoder,
                           final UserDetailsService userDetailsService,
                           final AuthenticationManager authenticationManager, final MessageSource messageSource) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.messageSource = messageSource;
    }

    @Override
    public Long loadUserIdByLogin(final String login) {
        return userDao.getUserIdByLogin(login);
    }

    @Override
    public void save(final UserRegistrationDto user) {
        if (Objects.isNull(userDao.getUserByLogin(user.getLogin()))) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Long userId = userDao.create(user);
            List<RoleDto> roleDtoList = user.getRoles()
                    .stream().map(roleId -> new RoleDto(userId, roleId)).collect(Collectors.toList());
            roleDao.createUserRole(roleDtoList);
        } else {
            throw new UserAlreadyExistException(messageSource.getMessage("user.alreadyExists",
                    new Object[]{user.getLogin()}, LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public void autoLogin(final String userName, final String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        UsernamePasswordAuthenticationToken usernamePasswordAuthentication =
                new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        authenticationManager.authenticate(usernamePasswordAuthentication);
        if (usernamePasswordAuthentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthentication);
        }
    }
}
