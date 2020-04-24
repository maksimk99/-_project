package com.epam.cdp.maksim.katuranau.module12.task1.service;


import com.epam.cdp.maksim.katuranau.module12.task1.model.Order;
import com.epam.cdp.maksim.katuranau.module12.task1.model.Status;

import java.util.List;

/**
 * The interface Order service.
 */
public interface OrderService {

    /**
     * Gets list order.
     *
     * @param userId the user id
     * @return the list order
     */
    List<Order> getListOrder(Long userId);

    /**
     * Submit order.
     *
     * @param userId the user id
     */
    void submitOrder(Long userId);

    /**
     * Update order status.
     *
     * @param orderId  the order id
     * @param statusId the status id
     */
    void updateOrderStatus(Long orderId, Long statusId);

    /**
     * Remove order.
     *
     * @param orderId the order id
     */
    void removeOrder(Long orderId);

    /**
     * Gets order by id.
     *
     * @param orderId the order id
     * @return the order by id
     */
    Order getOrderById(Long orderId);

    /**
     * Gets list status.
     *
     * @return the list status
     */
    List<Status> getListStatus();
}
