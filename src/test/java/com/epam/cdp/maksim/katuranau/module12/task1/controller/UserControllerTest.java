package com.epam.cdp.maksim.katuranau.module12.task1.controller;

import com.epam.cdp.maksim.katuranau.module12.task1.model.UserAuthorizationDto;
import com.epam.cdp.maksim.katuranau.module12.task1.model.UserRegistrationDto;
import com.epam.cdp.maksim.katuranau.module12.task1.service.RoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "maksimk99", authorities = {"USER", "ADMIN"})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RoleService roleService;

    @Test
    void getRegistration() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(model().attribute("selectableRoles", roleService.getRoles()))
                .andExpect(model().attribute("user", new UserRegistrationDto()));
    }

    @Test
    void registerUser() throws Exception {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setLogin("Kira25");
        userRegistrationDto.setPassword("12345");
        userRegistrationDto.setRoles(Arrays.asList(1, 2));
        mockMvc.perform(post("/register")
                .flashAttr("userRegistrationDto", userRegistrationDto))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void showAuthorizationPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("loginPage"))
                .andExpect(model().attribute("user", new UserAuthorizationDto()));
    }
}