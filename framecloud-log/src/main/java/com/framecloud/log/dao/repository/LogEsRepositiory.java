package com.framecloud.log.dao.repository;

import com.framecloud.log.model.entity.LogEs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface LogEsRepositiory extends ElasticsearchRepository<LogEs, String> {
}
