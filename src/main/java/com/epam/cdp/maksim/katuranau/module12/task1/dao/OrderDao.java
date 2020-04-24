package com.epam.cdp.maksim.katuranau.module12.task1.dao;

import com.epam.cdp.maksim.katuranau.module12.task1.model.Order;
import com.epam.cdp.maksim.katuranau.module12.task1.model.Status;

import java.util.List;

/**
 * The interface Order dao.
 */
public interface OrderDao {

    /**
     * Gets list order.
     *
     * @param userId the user id
     * @return the list order
     */
    List<Order> getListOrder(Long userId);

    /**
     * Submit list order long.
     *
     * @param userId the user id
     * @return the long
     */
    Long submitListOrder(Long userId);

    /**
     * Add goods to order.
     *
     * @param userId  the user id
     * @param orderId the order id
     */
    void addGoodsToOrder(Long userId, Long orderId);

    /**
     * Update order status boolean.
     *
     * @param orderId  the order id
     * @param statusId the status id
     * @return the boolean
     */
    boolean updateOrderStatus(Long orderId, Long statusId);

    /**
     * Remove order boolean.
     *
     * @param orderId the order id
     * @return the boolean
     */
    boolean removeOrder(Long orderId);

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
