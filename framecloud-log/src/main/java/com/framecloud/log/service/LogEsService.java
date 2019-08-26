package com.framecloud.log.service;

import com.framecloud.log.model.entity.LogEs;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LogEsService {

    long count();

    LogEs save(LogEs logEs);

    void delete(LogEs logEs);

    Iterable<LogEs> getAll();

    List<LogEs> getByName(String name);

    Page<LogEs> pageQuery(Integer pageNo, Integer pageSize, String kw);

}
