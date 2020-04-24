package com.epam.cdp.maksim.katuranau.module12.task1.service.impl;

import com.epam.cdp.maksim.katuranau.module12.task1.dao.RoleDao;
import com.epam.cdp.maksim.katuranau.module12.task1.model.Role;
import com.epam.cdp.maksim.katuranau.module12.task1.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(final RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public List<Role> getRoles() {
        return roleDao.getRoles();
    }
}
