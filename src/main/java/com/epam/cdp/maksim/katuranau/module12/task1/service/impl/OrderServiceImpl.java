package com.epam.cdp.maksim.katuranau.module12.task1.service.impl;

import com.epam.cdp.maksim.katuranau.module12.task1.dao.GoodsDao;
import com.epam.cdp.maksim.katuranau.module12.task1.dao.OrderDao;
import com.epam.cdp.maksim.katuranau.module12.task1.exception.ValidationException;
import com.epam.cdp.maksim.katuranau.module12.task1.model.Order;
import com.epam.cdp.maksim.katuranau.module12.task1.model.Status;
import com.epam.cdp.maksim.katuranau.module12.task1.service.OrderService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao;
    private GoodsDao goodsDao;
    private final MessageSourceService messageSourceService;
    private final Counter counter;

    @Autowired
    public OrderServiceImpl(final OrderDao orderDao, final GoodsDao goodsDao,
                            final MessageSourceService messageSourceService, final MeterRegistry meterRegistry) {
        this.orderDao = orderDao;
        this.goodsDao = goodsDao;
        this.messageSourceService = messageSourceService;
        this.counter = Counter.builder("submitted.orders")
                .tag("type", "ale")
                .description("The amount of submitted orders")
                .register(meterRegistry);
    }

    @Override
    public List<Order> getListOrder(final Long userId) {
        return orderDao.getListOrder(userId);
    }

    @Override
    @Transactional
    public void submitOrder(final Long userId) {
        counter.increment();
        if (goodsDao.checkOrAllGoodsAvailable(userId)) {
            Long orderId = orderDao.submitListOrder(userId);
            orderDao.addGoodsToOrder(userId, orderId);
            goodsDao.removeGoodsByUserId(userId);
        } else {
            throw new ValidationException(
                    messageSourceService.getLocaleMessage("order.errorBasketHasGoodsAvailable"));
        }
    }

    @Override
    public void updateOrderStatus(final Long orderId, final Long statusId) {
        if (!orderDao.updateOrderStatus(orderId, statusId)) {
            throw new ValidationException(
                    messageSourceService.getLocaleMessage("order.errorExistingOfOrder"));
        }
    }

    @Override
    public void removeOrder(final Long orderId) {
        if (!orderDao.removeOrder(orderId)) {
            throw new ValidationException(
                    messageSourceService.getLocaleMessage("order.errorExistingOfOrder"));
        }
    }

    @Override
    public Order getOrderById(final Long orderId) {
        try {
            return orderDao.getOrderById(orderId);
        } catch (EmptyResultDataAccessException ex) {
            throw new ValidationException(
                    messageSourceService.getLocaleMessage("order.errorExistingOfOrder"));
        }
    }

    @Override
    public List<Status> getListStatus() {
        return orderDao.getListStatus();
    }
}
