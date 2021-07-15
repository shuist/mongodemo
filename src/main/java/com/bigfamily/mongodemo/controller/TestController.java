package com.bigfamily.mongodemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class TestController {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * @description:MongoDB新增数据
     *
     * 区别
     * 插入重复数据
     * 　　insert: 若新增数据的主键已经存在，则会抛 org.springframework.dao.DuplicateKeyException 异常提示主键重复，不保存当前数据。
     * 　　save: 若新增数据的主键已经存在，则会对当前已经存在的数据进行修改操作。
     *
     * 批操作
     * 　　insert: 可以一次性插入一整个列表，而不用进行遍历操作，效率相对较高
     * 　　save: 需要遍历列表，进行一个个的插入
     *
     * ---------------------
     * 作者：宋发元
     * 来源：CSDN
     * 原文：https://blog.csdn.net/u011019141/article/details/89192757
     * 版权声明：本文为博主原创文章，转载请附上博文链接！
     *
     * @param
     * @return com.github.collection.common.util.Response
     * @author songfayuan
     * @date 2019/4/10 19:25
     */
    @PostMapping("/mongoSave")
    public String mongoSave(){
        Map<String, Object> map = new HashMap<>();
        //map.put("_id", "10086");
        map.put("name", "songfayuan");
        map.put("age", "26");
        map.put("wechat", "SFY54420");
        this.mongoTemplate.save(map, "collectionName1");
        this.mongoTemplate.insert(map, "collectionName2");
        return"新增成功";
    }

    /**
     * @description:MongoDB根据条件删除数据
     * @param
     * @return com.github.collection.common.util.Response
     * @author songfayuan
     * @date 2019/4/10 20:03
     */
    @DeleteMapping("/mongoRemove")
    public String mongoRemove(){
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").in("10086"));
        this.mongoTemplate.remove(query, "collectionName1");
        return"删除成功";
    }

    /**
     * @description:MongoDB根据条件更新数据
     * @param
     * @return com.github.collection.common.util.Response
     * @author songfayuan
     * @date 2019/4/10 20:24
     */
    @PostMapping("/mongoUpdate")
    public String mongoUpdate(){
        List<String> list = new ArrayList<>();
        list.add("10086");
        list.add("10000");
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").in(list));
        Update update = new Update();
        update.set("age", "120");
        update.set("updateTime", new Date());
        this.mongoTemplate.updateMulti(query, update,"collectionName1");
        return "更新成功";
    }

    /**
     * @description:MongoDB获取数据库中的所有文档集合
     * @param
     * @return com.github.collection.common.util.Response
     * @author songfayuan
     * @date 2019/4/10 20:33
     */
    @GetMapping("/mongoGetCollectionNames")
    public String mongoGetCollectionNames(){
        Set<String> set = this.mongoTemplate.getCollectionNames();
        return set.toString();
    }


    /**
     * @description:MongoDB根据条件查询一条数据
     * @param
     * @return com.github.collection.common.util.Response
     * @author songfayuan
     * @date 2019/4/10 20:46
     */
    @GetMapping("/mongoFindOne")
    public String mongoFindOne() {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is("10086"));
        return this.mongoTemplate.findOne(query, Map.class,"collectionName1").toString();
    }

    /**
     * @description:MongoDB根据条件查询列表数据
     * @param
     * @return com.github.collection.common.util.Response
     * @author songfayuan
     * @date 2019/4/10 20:53
     */
    @GetMapping("/mongoFindList")
    public String mongoFindList() {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is("songfayuan"));
        return this.mongoTemplate.find(query, Map.class,"collectionName1").toString();
    }

}
