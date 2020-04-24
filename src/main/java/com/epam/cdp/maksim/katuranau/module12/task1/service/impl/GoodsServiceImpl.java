package com.epam.cdp.maksim.katuranau.module12.task1.service.impl;

import com.epam.cdp.maksim.katuranau.module12.task1.dao.GoodsDao;
import com.epam.cdp.maksim.katuranau.module12.task1.exception.InternalServerException;
import com.epam.cdp.maksim.katuranau.module12.task1.exception.ValidationException;
import com.epam.cdp.maksim.katuranau.module12.task1.model.Goods;
import com.epam.cdp.maksim.katuranau.module12.task1.service.GoodsService;
import com.epam.cdp.maksim.katuranau.module12.task1.service.fileUpload.FileWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Value("${default.image.name}")
    private String DEFAULT_IMAGE_NAME;

    private final GoodsDao goodsDao;
    private final FileWriter fileWriter;
    private final MessageSourceService messageSourceService;

    @Autowired
    public GoodsServiceImpl(final GoodsDao goodsDao, final FileWriter fileWriter,
                            final MessageSourceService messageSourceService) {
        this.goodsDao = goodsDao;
        this.fileWriter = fileWriter;
        this.messageSourceService = messageSourceService;
    }

    @Override
    public List<Goods> getGoods(int pageIndex, final boolean sortIncreasing) {
        int pageSize = Integer.parseInt(messageSourceService.getLocaleMessage("page.size"));
        pageIndex--;
        int amountOfGoods = goodsDao.getAmountOfGoods().intValue();
        if ((pageIndex) * pageSize > amountOfGoods) {
            pageIndex = 0;
        }
        List<Goods> goodsList = goodsDao.getGoods(pageSize, pageIndex * pageSize);
        if (!sortIncreasing) {
            goodsList.sort(Comparator.comparing(Goods::getId).reversed());
        }
        return goodsList;
    }

    @Override
    public List<Goods> findGoodsByName(final String goodsName) {
        return goodsDao.findGoodsByName(goodsName);
    }

    @Override
    public List<Goods> getAllGoodsByUserId(final Long userId) {
        return goodsDao.getAllGoodsByUserId(userId);
    }

    @Override
    public void removeGoodsByGoodsIdAndUserId(final Long goodsId, final Long userId) {
        if (!goodsDao.isExistByGoodsIdAndUserId(goodsId, userId)) {
            throw new ValidationException(
                    messageSourceService.getLocaleMessage("goods.errorExistingOfGoods"));
        }
        if (!goodsDao.removeGoodsByGoodsIdAndUserId(goodsId, userId)) {
            throw new InternalServerException(
                    messageSourceService.getLocaleMessage("goods.errorRemovingFromBasket"));
        }
    }

    @Override
    public void removeGoodsByGoodsId(final Long goodsId) {
        if (!goodsDao.isExist(goodsId)) {
            throw new ValidationException(
                    messageSourceService.getLocaleMessage("goods.errorExistingOfGoods"));
        }
        String imageName = goodsDao.getGoodsImageName(goodsId);
        if (!goodsDao.removeGoodsByGoodsId(goodsId)) {
            throw new InternalServerException(
                    messageSourceService.getLocaleMessage("goods.errorRemovingGoods"));
        }
        fileWriter.deleteFile(imageName);
    }

    @Override
    public void addGoodsToBasketByGoodsIdAndUserId(final Long goodsId, final Long userId) {
        if (!goodsDao.isExist(goodsId)) {
            throw new ValidationException(
                    messageSourceService.getLocaleMessage("goods.errorExistingOfGoods"));
        }
        if (goodsDao.isExistByGoodsIdAndUserId(goodsId, userId)) {
            throw new ValidationException(
                    messageSourceService.getLocaleMessage("goods.alreadyExistsInTheBasket"));
        }
        if (!goodsDao.addGoodsToBasketByGoodsIdAndUserId(goodsId, userId)) {
            throw new InternalServerException(
                    messageSourceService.getLocaleMessage("goods.errorAddingInToBasket"));
        }
    }

    @Override
    public void create(final Goods goods, final MultipartFile image) {
        goods.setImageName(!image.isEmpty() ? UUID.randomUUID().toString() : DEFAULT_IMAGE_NAME);
        goodsDao.create(goods);
        if (!image.isEmpty()) {
            fileWriter.writeFile(image, goods.getImageName());
        }
    }

    @Override
    public Goods getGoodsByGoodsId(final Long goodsId) {
        if (!goodsDao.isExist(goodsId)) {
            throw new ValidationException(
                    messageSourceService.getLocaleMessage("goods.errorExistingOfGoods"));
        }
        return goodsDao.getGoodsByGoodsId(goodsId);
    }

    @Override
    public void update(final Goods goods, final MultipartFile image) {
        if (!goodsDao.isExist(goods.getId())) {
            throw new ValidationException(
                    messageSourceService.getLocaleMessage("goods.errorExistingOfGoods"));
        }
        String oldGoodsImageName = goods.getImageName();
        goods.setImageName(!image.isEmpty() ? UUID.randomUUID().toString() : oldGoodsImageName);
        if (!goodsDao.update(goods)) {
            throw new InternalServerException(
                    messageSourceService.getLocaleMessage("goods.errorUpdatingGoods"));
        }
        if (!image.isEmpty()) {
            fileWriter.deleteFile(oldGoodsImageName);
            fileWriter.writeFile(image, goods.getImageName());
        }
    }

    @Override
    public List<Integer> getPageNumbers() {
        int totalPages = (int) Math.ceil(goodsDao.getAmountOfGoods() /
                Integer.parseInt(messageSourceService.getLocaleMessage("page.size")));
        List<Integer> pageNumbers = null;
        if (totalPages > 0) {
            pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
        }
        return pageNumbers;
    }
}
