package com.epam.cdp.maksim.katuranau.module12.task1.dao.jdbc;

import com.epam.cdp.maksim.katuranau.module12.task1.dao.GoodsDao;
import com.epam.cdp.maksim.katuranau.module12.task1.dao.jdbc.mapper.GoodsRowMapper;
import com.epam.cdp.maksim.katuranau.module12.task1.model.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class GoodsDaoImpl implements GoodsDao {

    @Value("${goods.getAmount}")
    private String GOODS_AMOUNT_GET_SQL;
    @Value("${goods.selectAllGoods}")
    private String GOODS_LIST_GET_SQL;
    @Value("${goods.findByName}")
    private String GOODS_FIND_BY_NAME_SQL;
    @Value("${goods.insert}")
    private String GOODS_INSERT_SQL;
    @Value("${goods.selectAllGoodsByUserId}")
    private String GOODS_BY_USER_ID_GET_SQL;
    @Value("${goods.selectGoodsByGoodsId}")
    private String GOODS_BY_GOODS_ID_GET_SQL;
    @Value("${goods.removeGoodsByGoodsIdAndUserId}")
    private String GOODS_BY_GOODS_ID_AND_USER_ID_REMOVE_SQL;
    @Value("${goods.removeGoodsByGoodsId}")
    private String GOODS_BY_GOODS_ID_REMOVE_SQL;
    @Value("${goods.removeGoodsByUserId}")
    private String GOODS_BY_USER_ID_REMOVE_SQL;
    @Value("${goods.addGoodsByGoodsIdAndUserId}")
    private String GOODS_BY_GOODS_ID_AND_USER_ID_INSERT_SQL;
    @Value("${goods.checkGoodsByGoodsId}")
    private String CHECK_GOODS_BY_GOODS_ID_SQL;
    @Value("${goods.checkGoodsByGoodsIdAndUserId}")
    private String CHECK_GOODS_BY_GOODS_ID_AND_USER_ID_SQL;
    @Value("${goods.update}")
    private String GOODS_UPDATE_SQL;
    @Value("${goods.checkOrAllGoodsAvailable}")
    private String GOODS_CHECK_OR_ALL_GOODS_AVAILABLE;
    @Value("${goods.getImageName}")
    private String GET_GOODS_IMAGE_NAME_SQL;

    private final GoodsRowMapper goodsRowMapper;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public GoodsDaoImpl(
            final GoodsRowMapper goodsRowMapper,
            final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.goodsRowMapper = goodsRowMapper;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Double getAmountOfGoods() {
        return namedParameterJdbcTemplate.queryForObject(GOODS_AMOUNT_GET_SQL, new HashMap<>(), Double.class);
    }

    @Override
    public List<Goods> getGoods(final int limit, final int offset) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("limit", limit);
        mapSqlParameterSource.addValue("offset", offset);
        return namedParameterJdbcTemplate.query(GOODS_LIST_GET_SQL, mapSqlParameterSource, goodsRowMapper);
    }

    @Override
    public List<Goods> findGoodsByName(final String goodsName) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("goodsName", "%" + goodsName + "%");
        return namedParameterJdbcTemplate.query(GOODS_FIND_BY_NAME_SQL, mapSqlParameterSource, goodsRowMapper);
    }

    @Override
    public List<Goods> getAllGoodsByUserId(final Long userId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("userId", userId);
        return namedParameterJdbcTemplate.query(GOODS_BY_USER_ID_GET_SQL, mapSqlParameterSource, goodsRowMapper);
    }

    @Override
    public Boolean removeGoodsByGoodsIdAndUserId(
            final Long goodsId,
            final Long userId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("goodsId", goodsId);
        mapSqlParameterSource.addValue("userId", userId);
        return namedParameterJdbcTemplate.update(GOODS_BY_GOODS_ID_AND_USER_ID_REMOVE_SQL, mapSqlParameterSource) == 1;
    }

    @Override
    public Boolean removeGoodsByUserId(final Long userId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("userId", userId);
        return namedParameterJdbcTemplate.update(GOODS_BY_USER_ID_REMOVE_SQL, mapSqlParameterSource) > 0;
    }

    @Override
    public Boolean removeGoodsByGoodsId(final Long userId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("goodsId", userId);
        return namedParameterJdbcTemplate.update(GOODS_BY_GOODS_ID_REMOVE_SQL, mapSqlParameterSource) == 1;
    }

    @Override
    public Boolean addGoodsToBasketByGoodsIdAndUserId(
            final Long goodsId,
            final Long userId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("goodsId", goodsId);
        mapSqlParameterSource.addValue("userId", userId);
        return namedParameterJdbcTemplate.update(GOODS_BY_GOODS_ID_AND_USER_ID_INSERT_SQL, mapSqlParameterSource) == 1;
    }

    @Override
    public void create(final Goods goods) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("name", goods.getName());
        mapSqlParameterSource.addValue("price", goods.getPrice());
        mapSqlParameterSource.addValue("quantity", goods.getQuantity());
        mapSqlParameterSource.addValue("imageName", goods.getImageName());
        mapSqlParameterSource.addValue("description", goods.getDescription());
        namedParameterJdbcTemplate.update(GOODS_INSERT_SQL, mapSqlParameterSource);
    }

    @Override
    public Boolean isExist(final Long goodsId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("goodsId", goodsId);
        return namedParameterJdbcTemplate.queryForObject(CHECK_GOODS_BY_GOODS_ID_SQL, mapSqlParameterSource,
                Boolean.class);
    }

    @Override
    public Boolean isExistByGoodsIdAndUserId(
            final Long goodsId,
            final Long userId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("goodsId", goodsId);
        mapSqlParameterSource.addValue("userId", userId);
        return namedParameterJdbcTemplate.queryForObject(CHECK_GOODS_BY_GOODS_ID_AND_USER_ID_SQL, mapSqlParameterSource,
                Boolean.class);
    }

    @Override
    public Goods getGoodsByGoodsId(final Long goodsId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("goodsId", goodsId);
        return namedParameterJdbcTemplate.queryForObject(GOODS_BY_GOODS_ID_GET_SQL, mapSqlParameterSource,
                goodsRowMapper);
    }

    @Override
    public Boolean update(final Goods goods) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", goods.getId());
        mapSqlParameterSource.addValue("name", goods.getName());
        mapSqlParameterSource.addValue("price", goods.getPrice());
        mapSqlParameterSource.addValue("quantity", goods.getQuantity());
        mapSqlParameterSource.addValue("imageName", goods.getImageName());
        mapSqlParameterSource.addValue("description", goods.getDescription());
        return namedParameterJdbcTemplate.update(GOODS_UPDATE_SQL, mapSqlParameterSource) == 1;
    }

    @Override
    public Boolean checkOrAllGoodsAvailable(final Long userId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("userId", userId);
        return namedParameterJdbcTemplate.queryForObject(GOODS_CHECK_OR_ALL_GOODS_AVAILABLE, mapSqlParameterSource,
                Boolean.class);
    }

    @Override
    public String getGoodsImageName(Long goodsId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("goodsId", goodsId);
        return namedParameterJdbcTemplate.queryForObject(GET_GOODS_IMAGE_NAME_SQL, parameters, String.class);
    }
}
