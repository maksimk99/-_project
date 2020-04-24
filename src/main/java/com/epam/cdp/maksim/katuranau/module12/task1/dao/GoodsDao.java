package com.epam.cdp.maksim.katuranau.module12.task1.dao;

import com.epam.cdp.maksim.katuranau.module12.task1.model.Goods;

import java.util.List;

/**
 * The interface Goods dao.
 */
public interface GoodsDao {

    /**
     * Gets amount of goods.
     *
     * @return the amount of goods
     */
    Double getAmountOfGoods();

    /**
     * Gets all goods.
     *
     * @param limit  the index from
     * @param offset the index to
     * @return the all goods
     */
    List<Goods> getGoods(int limit, int offset);

    /**
     * Find goods by name list.
     *
     * @param goodsName the goods name
     * @return the list
     */
    List<Goods> findGoodsByName(String goodsName);

    /**
     * Gets all goods by user id.
     *
     * @param userId the user id
     * @return the all goods by user id
     */
    List<Goods> getAllGoodsByUserId(Long userId);

    /**
     * Remove goods by goods id and user id boolean.
     *
     * @param goodsId the goods id
     * @param userId  the user id
     * @return the boolean
     */
    Boolean removeGoodsByGoodsIdAndUserId(Long goodsId, Long userId);

    /**
     * Remove goods by user id boolean.
     *
     * @param userId the user id
     * @return the boolean
     */
    Boolean removeGoodsByUserId(Long userId);

    /**
     * Remove goods by goods id boolean.
     *
     * @param goodsId the goods id
     * @return the boolean
     */
    Boolean removeGoodsByGoodsId(Long goodsId);

    /**
     * Add goods to basket by goods id and user id boolean.
     *
     * @param goodsId the goods id
     * @param userId  the user id
     * @return the boolean
     */
    Boolean addGoodsToBasketByGoodsIdAndUserId(Long goodsId, Long userId);

    /**
     * Create.
     *
     * @param goods the goods
     */
    void create(Goods goods);

    /**
     * Is exist boolean.
     *
     * @param goodsId the goods id
     * @return the boolean
     */
    Boolean isExist(Long goodsId);

    /**
     * Is exist by goods id and user id boolean.
     *
     * @param goodsId the goods id
     * @param userId  the user id
     * @return the boolean
     */
    Boolean isExistByGoodsIdAndUserId(Long goodsId, Long userId);

    /**
     * Gets goods by goods id.
     *
     * @param goodsId the goods id
     * @return the goods by goods id
     */
    Goods getGoodsByGoodsId(Long goodsId);

    /**
     * Update boolean.
     *
     * @param goods the goods
     * @return the boolean
     */
    Boolean update(Goods goods);

    /**
     * Check or all goods from the basket are available for ordering,
     * true when the number of not available goods equal 0
     * @param userId user id to check
     * @return boolean
     */
    Boolean checkOrAllGoodsAvailable(Long userId);

    /**
     * Gets only image name of goods by goods id.
     * @param goodsId gods id to get image name
     * @return image name
     */
    String getGoodsImageName(Long goodsId);
}
