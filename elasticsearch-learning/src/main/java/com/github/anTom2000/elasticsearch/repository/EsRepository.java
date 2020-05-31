package com.github.anTom2000.elasticsearch.repository;

import com.github.anTom2000.elasticsearch.bean.DocBean;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Description : TODO  持久层操作接口，封装了最基本的crud方法
 * @Author : Weleness
 * @Date : 2020/05/31
 */
public interface EsRepository extends ElasticsearchRepository<DocBean,Long> {
}
