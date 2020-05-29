package com.example.demo.module.goods.service;

import com.example.demo.module.goods.model.Goods;

public interface GoodsService {

    void addGoodsInfo(Goods goods);

    Goods getGoods(String name);
}
