package com.framecloud.log.service.impl;

import com.framecloud.log.dao.repository.LogEsRepositiory;
import com.framecloud.log.model.entity.LogEs;
import com.framecloud.log.service.LogEsService;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import java.util.ArrayList;
import java.util.List;

public class LogEsServiceImpl implements LogEsService {

    @Autowired
    private LogEsRepositiory logEsRepositiory;

    @Override
    public long count() {
        return logEsRepositiory.count();
    }

    @Override
    public LogEs save(LogEs logEs) {
        return logEsRepositiory.save(logEs);
    }

    @Override
    public void delete(LogEs logEs) {
        logEsRepositiory.delete(logEs);
    }

    @Override
    public Iterable<LogEs> getAll() {
        return logEsRepositiory.findAll();
    }

    @Override
    public List<LogEs> getByName(String name) {
        List<LogEs> list = new ArrayList<>();
        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("name", name);
        Iterable<LogEs> iterable = logEsRepositiory.search(matchQueryBuilder);
        iterable.forEach(e->list.add(e));
        return list;
    }

    @Override
    public Page<LogEs> pageQuery(Integer pageNo, Integer pageSize, String kw) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchPhraseQuery("name", kw))
                .withPageable(PageRequest.of(pageNo, pageSize))
                .build();
        return logEsRepositiory.search(searchQuery);
    }
}
