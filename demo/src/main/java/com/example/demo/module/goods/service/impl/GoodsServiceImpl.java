package com.example.demo.module.goods.service.impl;

import com.example.demo.module.goods.dao.GoodsDao;
import com.example.demo.module.goods.model.Goods;
import com.example.demo.module.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @auther chenyong
 * @date 2020/5/28 15:35
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsDao goodsDao;

    @Override
    public void addGoodsInfo(Goods goods) {
        goodsDao.insertGoods(goods);
    }

    @Override
    public Goods getGoods(String name) {
        return goodsDao.getGoods(name);
    }
}
