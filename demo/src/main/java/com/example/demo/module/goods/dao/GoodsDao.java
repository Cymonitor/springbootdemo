package com.example.demo.module.goods.dao;

import com.example.demo.module.goods.model.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

public interface GoodsDao {

    void insertGoods(@Param("goods") Goods goods);

    Goods getGoods(String name);
}
