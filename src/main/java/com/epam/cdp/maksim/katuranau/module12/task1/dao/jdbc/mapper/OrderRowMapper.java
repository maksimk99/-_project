package com.epam.cdp.maksim.katuranau.module12.task1.dao.jdbc.mapper;

import com.epam.cdp.maksim.katuranau.module12.task1.model.Goods;
import com.epam.cdp.maksim.katuranau.module12.task1.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class OrderRowMapper implements RowMapper<Order> {

    private static final String ORDER_ID = "order_id";

    private StatusRowMapper statusRowMapper;
    private GoodsRowMapper goodsRowMapper;

    @Autowired
    public OrderRowMapper(final StatusRowMapper statusRowMapper, final GoodsRowMapper goodsRowMapper) {
        this.statusRowMapper = statusRowMapper;
        this.goodsRowMapper = goodsRowMapper;
    }

    @Override
    public Order mapRow(ResultSet resultSet, int i) throws SQLException {
        long orderId = resultSet.getLong(ORDER_ID);
        Order order = new Order(orderId, statusRowMapper.mapRow(resultSet, i));
        List<Goods> goodsList = new ArrayList<>();
        Integer totalPrice = 0;
        Goods goods;
        do {
            goods = goodsRowMapper.mapRow(resultSet, i);
            if (Objects.nonNull(goods.getName())) {
                goodsList.add(goods);
                totalPrice += goods.getPrice();
            }
        } while (resultSet.next() && orderId == resultSet.getLong(ORDER_ID));
        if (!resultSet.isAfterLast()) {
            resultSet.previous();
        }
        order.setGoodsList(goodsList);
        order.setTotalPrice(totalPrice);
        return order;
    }
}
