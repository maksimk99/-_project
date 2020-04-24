package com.epam.cdp.maksim.katuranau.module12.task1.controller;

import com.epam.cdp.maksim.katuranau.module12.task1.service.OrderService;
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
@WithMockUser(username = "maksimk99", authorities = {"USER", "ADMIN"})
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OrderService orderService;

    @Test
    void updateOrderStatus() throws Exception {
        long orderId = 2;
        mockMvc.perform(get("/order/{orderId}/update", orderId))
                .andExpect(view().name("orderUpdate"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attribute("order", orderService.getOrderById(orderId)))
                .andExpect(model().attribute("statusList", orderService.getListStatus()));
    }

    @Test
    void updateStatusSubmit() throws Exception {
        mockMvc.perform(post("/order/update")
                .param("orderId", "2")
                .param("statusId", "3"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void deleteOrderById() throws Exception {
        mockMvc.perform(delete("/order")
                .param("orderId", "1"))
                .andExpect(status().is2xxSuccessful());
    }
}