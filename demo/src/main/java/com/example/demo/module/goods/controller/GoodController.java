package com.example.demo.module.goods.controller;

import com.example.demo.module.goods.model.Goods;
import com.example.demo.module.goods.service.GoodsService;
import com.example.demo.module.utils.UUIDUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther chenyong
 * @date 2020/5/28 15:27
 */
@RestController
@RequestMapping(value = "/goods/")
public class GoodController {

    @Autowired
    private GoodsService goodsService;

    @RequestMapping(value = "add",method = RequestMethod.POST)
    public void addGoods(Goods goods){
        goods.setId(UUIDUtils.getUUIDRandom());
        goodsService.addGoodsInfo(goods);
    }
}
