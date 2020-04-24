package com.epam.cdp.maksim.katuranau.module12.task1.controller;

import com.epam.cdp.maksim.katuranau.module12.task1.model.Goods;
import com.epam.cdp.maksim.katuranau.module12.task1.service.GoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Goods controller.
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsController.class);

    private static final String COOKIE_NAME = "numberOfVisits";

    private final GoodsService goodsService;

    /**
     * Instantiates a new Goods controller.
     *
     * @param goodsService the goods service
     */
    @Autowired
    public GoodsController(
            final GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    /**
     * Gets goods.
     *
     * @param pageIndex      the page index
     * @param sortIncreasing the sort increasing
     * @param numberOfVisits the number of visits
     * @param response       the response
     * @param model          the model
     * @return the goods
     */
    @GetMapping
    public String getGoods(@RequestParam(value = "pageIndex", defaultValue = "1") final int pageIndex,
                           @RequestParam(value = "sortIncreasing", defaultValue = "true") final boolean sortIncreasing,
                           @CookieValue(value = COOKIE_NAME, defaultValue = "0") final Integer numberOfVisits,
                           final HttpServletResponse response, final Model model) {
        LOGGER.info("getGoods method with pageIndex = {}, sortIncreasing = {} and numberOfVisits = {}", pageIndex,
                sortIncreasing, numberOfVisits);
        model.addAttribute("sortIncreasing", sortIncreasing);
        model.addAttribute("currentPage", pageIndex);
        model.addAttribute("pageNumbers", goodsService.getPageNumbers());
        model.addAttribute("goods", goodsService.getGoods(pageIndex, sortIncreasing));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> roleList = authentication.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        model.addAttribute("rolles", roleList);
        model.addAttribute("numberOfVisits", numberOfVisits + 1);
        response.addCookie(new Cookie(COOKIE_NAME, String.valueOf(numberOfVisits + 1)));
        return "goods";
    }

    @GetMapping("/{goodsId}")
    public String getGoodsDetails(@PathVariable final Long goodsId, final Model model) {
        model.addAttribute("goods", goodsService.getGoodsByGoodsId(goodsId));
        return "goodsDetails";
    }

    /**
     * Find goods by name string.
     *
     * @param goodsName the goods name
     * @param model     the model
     * @return the string
     */
    @PostMapping("/find")
    public String findGoodsByName(@RequestParam final String goodsName, final Model model) {
        LOGGER.info("findGoodsByName with goodsName = \"{}\"", goodsName);
        model.addAttribute("goods", goodsService.findGoodsByName(goodsName));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> roleList = authentication.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        model.addAttribute("rolles", roleList);
        return "goods";
    }

    /**
     * Remove goods by goods id.
     *
     * @param goodsId the goods id
     */
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void removeGoodsByGoodsId(@RequestParam final Long goodsId) {
        LOGGER.info("removeGoodsByGoodsId with goodsId = {}", goodsId);
        goodsService.removeGoodsByGoodsId(goodsId);
    }

    /**
     * Gets goods creation page.
     *
     * @param model the model
     * @return the goods creation page
     */
    @GetMapping(value = "/create")
    public String getGoodsCreationPage(final Model model) {
        LOGGER.info("getGoodsCreationPage method was invoked");
        model.addAttribute("goods", new Goods());
        return "goodsCreation";
    }

    /**
     * Create goods string.
     *
     * @param goods         the goods
     * @param bindingResult the binding result
     * @return the string
     */
    @PostMapping(value = "/create")
    public String createGoods(@ModelAttribute @Valid final Goods goods, final BindingResult bindingResult,
                              @RequestParam(required = false, value = "multipartFile") final MultipartFile image) {
        LOGGER.info("createGoods method [{}]", goods);
        if (bindingResult.hasErrors()) {
            LOGGER.warn("createGoods method has validation errors");
            return "goodsCreation";
        }
        goodsService.create(goods, image);
        return "redirect:/goods";
    }

    /**
     * Gets goods update page.
     *
     * @param goodsId the goods id
     * @param model   the model
     * @return the goods update page
     */
    @GetMapping(value = "/update")
    public String getGoodsUpdatePage(
            @RequestParam(value = "goodsId") final Long goodsId, final Model model) {
        LOGGER.info("getGoodsUpdatePage with goodsId = {}", goodsId);
        model.addAttribute("goods", goodsService.getGoodsByGoodsId(goodsId));
        return "goodsUpdate";
    }

    /**
     * Update goods.
     *
     * @param goods         the goods
     * @param bindingResult the binding result
     * @return the string
     */
    @PostMapping(value = "/update")
    public String updateGoods(@ModelAttribute @Valid final Goods goods, final BindingResult bindingResult,
                              @RequestParam(required = false, value = "multipartFile") final MultipartFile image) {
        LOGGER.info("updateGoods method [{}]", goods);
        if (bindingResult.hasErrors()) {
            LOGGER.warn("updateGoods method has validation errors");
            return "goodsUpdate";
        }
        goodsService.update(goods, image);
        return "redirect:/goods";
    }
}
