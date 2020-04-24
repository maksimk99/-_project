package com.epam.cdp.maksim.katuranau.module12.task1.controller;

import com.epam.cdp.maksim.katuranau.module12.task1.model.Goods;
import com.epam.cdp.maksim.katuranau.module12.task1.service.GoodsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "maksimk99", authorities = "USER")
class GoodsControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private GoodsService goodsService;

    @Test
    void getGoods() throws Exception {
        int pageIndex = 1;
        boolean sortIncreasing = true;
        mockMvc.perform(get("/goods")
                .param("pageIndex", String.valueOf(pageIndex))
                .param("sortIncreasing", String.valueOf(true)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("currentPage", pageIndex))
                .andExpect(model().attribute("sortIncreasing", sortIncreasing))
                .andExpect(model().attribute("pageNumbers", goodsService.getPageNumbers()))
                .andExpect(model().attribute("goods", goodsService.getGoods(pageIndex, sortIncreasing)));
    }

    @Test
    void findGoodsByName() throws Exception {
        String goodsName = "chair";
        mockMvc.perform(post("/goods/find")
                .param("goodsName", goodsName))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void removeGoodsByGoodsId() throws Exception {
        mockMvc.perform(delete("/goods")
                .param("goodsId", "1"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void getGoodsCreationPage() throws Exception {
        mockMvc.perform(get("/goods/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("goodsCreation"))
                .andExpect(model().attribute("goods", new Goods()));
    }

    @Test
    void createGoods() throws Exception {
        Goods goods = new Goods().setName("backpack").setDescription("has a beautiful color").setQuantity(3).setPrice(32);
        mockMvc.perform(multipart("/goods/create")
                .file("multipartFile", "".getBytes())
                .flashAttr("goods", goods))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void getGoodsUpdatePage() throws Exception {
        int goodsId = 3;
        mockMvc.perform(get("/goods/update")
                .param("goodsId", String.valueOf(goodsId)))
                .andExpect(status().isOk())
                .andExpect(view().name("goodsUpdate"))
                .andExpect(model().attribute("goods", goodsService.getGoodsByGoodsId((long) goodsId)));
    }

    @Test
    void updateGoods() throws Exception {
        Goods goods = goodsService.getGoodsByGoodsId((long) 1)
                .setName("earphones").setDescription("has good sound quality");
        mockMvc.perform(multipart("/goods/update")
                .file("multipartFile", "".getBytes())
                .flashAttr("goods", goods))
                .andExpect(status().is3xxRedirection());
    }
}