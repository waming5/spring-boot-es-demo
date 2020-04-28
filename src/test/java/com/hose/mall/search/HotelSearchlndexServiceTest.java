package com.hose.mall.search;

import com.hose.mall.search.index.IndexBasicsHandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author wangmi wangmi@hosecloud.com
 * @version 1.0
 * @Copyright (c) 合思技术团队 https://www.ekuaibao.com/
 * @Package com.hose.mall.search
 * @Project mall-tool
 * @date 2020/4/26 1:18 上午
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HotelSearchlndexServiceTest {
    @Autowired
    private IndexBasicsHandleService hotelSearchlndexService;

    private String indexName="hotel_v1_index";
    private String alias="hotel_index";
    private String indexType="hotel";

    @Test
    public void create(){
        boolean rs=hotelSearchlndexService.createIndex(indexName,indexType);
        System.out.println(rs);
    }

    @Test
    public void exist(){
        boolean rs=hotelSearchlndexService.isIndexExist(indexName);
        System.out.println(rs);
    }

    @Test
    public void delele(){
        boolean rs=hotelSearchlndexService.deleteIndex(indexName);
        System.out.println(rs);
    }


    @Test
    public void alias(){
        boolean rs=hotelSearchlndexService.addIndexAlias(indexName,alias);
        System.out.println(rs);
    }

    @Test
    public void removeAlias(){
        boolean rs=hotelSearchlndexService.deleteAlias(indexName,alias);
        System.out.println(rs);
    }


}
