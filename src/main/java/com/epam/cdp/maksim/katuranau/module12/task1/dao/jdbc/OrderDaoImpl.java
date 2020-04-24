package com.epam.cdp.maksim.katuranau.module12.task1.dao.jdbc;

import com.epam.cdp.maksim.katuranau.module12.task1.dao.OrderDao;
import com.epam.cdp.maksim.katuranau.module12.task1.dao.jdbc.mapper.OrderRowMapper;
import com.epam.cdp.maksim.katuranau.module12.task1.dao.jdbc.mapper.StatusRowMapper;
import com.epam.cdp.maksim.katuranau.module12.task1.model.Order;
import com.epam.cdp.maksim.katuranau.module12.task1.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {

    @Value("${orders.list.get}")
    private String ORDERS_LIST_GET_SQL;
    @Value("${orders.get.by.id}")
    private String ORDERS_GET_BY_ID_SQL;
    @Value("${orders.submit.by.user.id}")
    private String ORDER_BY_USER_ID_SUBMIT_SQL;
    @Value("${orders.add.goods}")
    private String ORDER_ADD_GOODS_SQL;
    @Value("${orders.update.order.status}")
    private String ORDER_UPDATE_STATUS_SQL;
    @Value("${orders.remove.by.id}")
    private String ORDER_REMOVE_BY_ID_SQL;
    @Value("${orders.get.available.status.list}")
    private String ORDER_STATUS_LIST_GET_SQL;

    private final OrderRowMapper orderRowMapper;
    private final StatusRowMapper statusRowMapper;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public OrderDaoImpl(final OrderRowMapper orderRowMapper, final StatusRowMapper statusRowMapper,
                        final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.orderRowMapper = orderRowMapper;
        this.statusRowMapper = statusRowMapper;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Order> getListOrder(final Long userId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("userId", userId);
        return namedParameterJdbcTemplate.query(ORDERS_LIST_GET_SQL, mapSqlParameterSource, orderRowMapper);
    }

    @Override
    public Long submitListOrder(final Long userId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("userId", userId);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(ORDER_BY_USER_ID_SUBMIT_SQL, mapSqlParameterSource, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public void addGoodsToOrder(final Long userId, final Long orderId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("userId", userId);
        mapSqlParameterSource.addValue("orderId", orderId);
        namedParameterJdbcTemplate.update(ORDER_ADD_GOODS_SQL, mapSqlParameterSource);
    }

    @Override
    public boolean updateOrderStatus(final Long orderId, final Long statusId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("orderId", orderId);
        mapSqlParameterSource.addValue("statusId", statusId);
        return namedParameterJdbcTemplate.update(ORDER_UPDATE_STATUS_SQL, mapSqlParameterSource) == 1;
    }

    @Override
    public boolean removeOrder(final Long orderId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("orderId", orderId);
        return namedParameterJdbcTemplate.update(ORDER_REMOVE_BY_ID_SQL, mapSqlParameterSource) == 1;
    }

    @Override
    public Order getOrderById(final Long orderId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("orderId", orderId);
        return namedParameterJdbcTemplate.queryForObject(ORDERS_GET_BY_ID_SQL, mapSqlParameterSource, orderRowMapper);
    }

    @Override
    public List<Status> getListStatus() {
        return namedParameterJdbcTemplate.query(ORDER_STATUS_LIST_GET_SQL, statusRowMapper);
    }
}
