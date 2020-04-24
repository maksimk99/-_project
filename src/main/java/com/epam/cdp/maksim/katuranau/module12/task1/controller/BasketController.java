package com.epam.cdp.maksim.katuranau.module12.task1.controller;

import com.epam.cdp.maksim.katuranau.module12.task1.service.GoodsService;
import com.epam.cdp.maksim.katuranau.module12.task1.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.constraints.Min;

/**
 * The type Basket controller.
 */
@Controller
@Validated
@RequestMapping("/basket")
public class BasketController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasketController.class);

    private final GoodsService goodsService;
    private final UserService userService;

    /**
     * Instantiates a new Basket controller.
     *
     * @param goodsService the goods service
     * @param userService  the user service
     */
    @Autowired
    public BasketController(
            final GoodsService goodsService, final UserService userService) {
        this.goodsService = goodsService;
        this.userService = userService;
    }

    /**
     * Gets goods by user id.
     *
     * @param model the model
     * @return the goods by user id
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String getGoodsByUserId(final Model model) {
        LOGGER.info("getGoodsByUserId method was invoked");
        model.addAttribute("goods", goodsService.getAllGoodsByUserId((getUserId())));
        return "basket";
    }

    /**
     * Add goods to basket by goods id and user id.
     *
     * @param goodsId the goods id
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addGoodsToBasketByGoodsId(
            @RequestParam @Min(value = 0, message = "goods id should not be negative") final Long goodsId) {
        LOGGER.info("addGoodsToBasketByGoodsId method with goodsId = {} was invoked", goodsId);
        goodsService.addGoodsToBasketByGoodsIdAndUserId(goodsId, getUserId());
    }

    /**
     * Remove goods by goods id and user id.
     *
     * @param goodsId the goods id
     */
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void removeGoodsByGoodsIdAndUserId(
            @RequestParam @Min(value = 0, message = "goods id should not be negative") final Long goodsId) {
        LOGGER.info("removeGoodsByGoodsIdAndUserId method with goodsId = {} was invoked", goodsId);
        goodsService.removeGoodsByGoodsIdAndUserId(goodsId, getUserId());
    }

    private Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.loadUserIdByLogin(authentication.getName());
    }
}
