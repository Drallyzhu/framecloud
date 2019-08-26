package com.framecloud.log.controller;


import com.framecloud.log.model.entity.LogEs;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/loges")
public class SysEsController {

    @Autowired
    public ElasticsearchTemplate elasticsearchTemplate;


    @PostMapping("/testInsert")
    public void testInsert(){
        LogEs logEs = new LogEs();
        logEs.setLogId("1501009005");
        logEs.setActionName("葡萄吐司面包（10片装）");
        logEs.setCreateTime(LocalDateTime.now());
        logEs.setCreateBy("ZJR");
        IndexQuery indexQuery = new IndexQueryBuilder().withObject(logEs).build();
        elasticsearchTemplate.index(indexQuery);
    }

    @ResponseBody
    @PostMapping("/testfind")
    public List testfind(){
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("actionName", "吐司"))
                .build();
        List<LogEs> list = elasticsearchTemplate.queryForList(searchQuery, LogEs.class);
        return list;
    }

    @ResponseBody
    @PostMapping("/callBack")
    public String callBack(){
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("actionName", "吐司"))
                .build();
        List<LogEs> list = elasticsearchTemplate.queryForList(searchQuery, LogEs.class);
        return "";
    }





}
