package com.epam.cdp.maksim.katuranau.module12.task1.controller;

import com.epam.cdp.maksim.katuranau.module12.task1.service.OrderService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.constraints.Min;

/**
 * The type Order controller.
 */
@Controller
@Validated
@RequestMapping("/order")
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    private final UserService userService;
    private final OrderService orderService;

    /**
     * Instantiates a new Order controller.
     *
     * @param userService  the user service
     * @param orderService the order service
     */
    @Autowired
    public OrderController(final UserService userService, final OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    /**
     * Gets orders.
     *
     * @param model the model
     * @return the orders
     */
    @GetMapping
    public String getOrders(final Model model) {
        LOGGER.info("getOrders method was invoked");
        model.addAttribute("listOrders", orderService.getListOrder(getUserId()));
        return "orders";
    }

    /**
     * Submit order string.
     *
     * @return the string
     */
    @GetMapping("/submit")
    public String submitOrder() {
        LOGGER.info("submitOrder method was invoked");
        orderService.submitOrder(getUserId());
        return "redirect:/order";
    }

    /**
     * Update order status string.
     *
     * @param orderId the order id
     * @param model   the model
     * @return the string
     */
    @GetMapping("/{orderId}/update")
    public String updateOrderStatus(@PathVariable @Min(value = 0, message = "order id should not be negative") final Long orderId, final Model model) {
        LOGGER.info("updateOrderStatus method with orderId = {}", orderId);
        model.addAttribute("order", orderService.getOrderById(orderId));
        model.addAttribute("statusList", orderService.getListStatus());
        return "orderUpdate";
    }

    /**
     * Update status submit string.
     *
     * @param orderId  the order id
     * @param statusId the status id
     * @return the string
     */
    @PostMapping("/update")
    public String updateStatusSubmit(
            @RequestParam @Min(value = 0, message = "order id should not be negative") final Long orderId,
            @RequestParam @Min(value = 0, message = "status id should not be negative") final Long statusId) {
        LOGGER.info("updateStatusSubmit method with orderId = {} and statusId = {}", orderId, statusId);
        orderService.updateOrderStatus(orderId, statusId);
        return "redirect:/order";
    }

    /**
     * Delete order by id.
     *
     * @param orderId the order id
     */
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteOrderById(
            @RequestParam @Min(value = 0, message = "order id should not be negative") final Long orderId) {
        LOGGER.info("deleteOrderById method with orderId = {}", orderId);
        orderService.removeOrder(orderId);
    }

    private Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.loadUserIdByLogin(authentication.getName());
    }
}
