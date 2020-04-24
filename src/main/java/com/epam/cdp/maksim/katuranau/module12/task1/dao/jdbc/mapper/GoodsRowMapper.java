package com.epam.cdp.maksim.katuranau.module12.task1.dao.jdbc.mapper;

import com.epam.cdp.maksim.katuranau.module12.task1.model.Goods;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GoodsRowMapper implements RowMapper<Goods> {

    private static final String GOODS_ID = "id";
    private static final String GOODS_NAME = "name";
    private static final String GOODS_SOLD = "sold";
    private static final String GOODS_PRICE = "price";
    private static final String IMAGE_NAME = "image_name";
    private static final String GOODS_QUANTITY = "quantity";
    private static final String GOODS_DESCRIPTION = "description";


    @Override
    public Goods mapRow(final ResultSet resultSet, final int i) throws SQLException {
        return new Goods()
                .setId(resultSet.getLong(GOODS_ID))
                .setPrice(resultSet.getInt(GOODS_PRICE))
                .setName(resultSet.getString(GOODS_NAME))
                .setQuantity(resultSet.getInt(GOODS_QUANTITY))
                .setImageName(resultSet.getString(IMAGE_NAME))
                .setDescription(resultSet.getString(GOODS_DESCRIPTION))
                .setAvailableQuantity(resultSet.getInt(GOODS_QUANTITY) - resultSet.getInt(GOODS_SOLD));
    }
}
