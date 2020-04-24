package com.epam.cdp.maksim.katuranau.module12.task1.controller;

import com.epam.cdp.maksim.katuranau.module12.task1.service.GoodsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "maksimk99")
class BasketControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private GoodsService goodsService;

    @Test
    void getGoodsByUserId() throws Exception {
        mockMvc.perform(get("/basket"))
                .andExpect(status().isOk())
                .andExpect(view().name("basket"))
                .andExpect(model().attribute("goods", goodsService.getAllGoodsByUserId((long) 1)));
    }

    @Test
    void addGoodsToBasketByGoodsId() throws Exception {
        mockMvc.perform(post("/basket")
                .param("goodsId", "2"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void removeGoodsByGoodsIdAndUserId() throws Exception {
        mockMvc.perform(delete("/basket")
                .param("goodsId", "1"))
                .andExpect(status().is2xxSuccessful());
    }
}