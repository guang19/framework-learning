package com.github.elasticsearchlearning;

import com.github.anTom2000.elasticsearch.bean.DocBean;
import com.github.anTom2000.elasticsearch.repository.EsRepository;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ElasticsearchLearningApplicationTests {

   Logger log = LoggerFactory.getLogger(ElasticsearchLearningApplicationTests.class);

   @Autowired
   EsRepository esRepository;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    /**
     * @Method: createIndex
     * @DATE: 2020/5/31
     * @Description: TODO  创建索引
     * @Author Weleness
     * @param
     * @Return void
     */
    @Test
    void createIndex(){
        elasticsearchRestTemplate.createIndex(DocBean.class);
    }

    /**
     * @Method: save
     * @DATE: 2020/5/31
     * @Description: TODO  保存一条记录
     * @Author Weleness
     * @param
     * @Return void
     */
    @Test
    void save(){
        DocBean docBean = new DocBean(1L,"张三","123456",18,"一个叫张三的学生");
        DocBean bean = esRepository.save(docBean);
        log.info("[bean]={}",bean);
    }

    /**
     * @Method: savaList
     * @DATE: 2020/5/31
     * @Description: TODO  批量保存
     * @Author Weleness
     * @param
     * @Return
     */
    @Test
    void saveList(){
        List<DocBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
           DocBean docBean = new DocBean(Long.parseLong(i+""),"李四"+i,"132"+i,10+i,"批量"+i);
            list.add(docBean);
        }
        Iterable<DocBean> docBeans = esRepository.saveAll(list);
        docBeans.forEach(docBean -> log.info("[docBean]={}",docBean));
    }

    /**
     * @Method: search
     * @DATE: 2020/5/31
     * @Description: TODO  查询所有
     * @Author Weleness
     * @param
     * @Return
     */
    @Test
    void search(){
        Iterable<DocBean> docBeans = esRepository.findAll();
        docBeans.forEach(docBean-> log.info("[student] = {}",docBean));
    }

   /**
    * @Method: delete
    * @DATE: 2020/5/31
    * @Description: TODO  删除
    * @Author Weleness
    * @param
    * @Return
    */
    @Test
    void delete(){
        esRepository.deleteById(1L);
    }

    /**
     * @Method: advanceSearch
     * @DATE: 2020/5/31
     * @Description: TODO  高级查询
     * @Author Weleness
     * @param
     * @Return
     */
    @Test
    void advanceSearch(){
        //MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
        //esRepository.search(matchAllQueryBuilder).forEach(student -> {
        //    logger.info("[student]={}",student);
        //});
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("name", "张三");
        esRepository.search(matchQueryBuilder).forEach(docBean -> log.info("[student]={}",docBean));
    }


    /**
     * @Method: customAdvanceSearch
     * @DATE: 2020/5/31
     * @Description: TODO  自定义高级查询
     * @Author Weleness
     * @param
     * @Return
     */
    @Test
    void customAdvanceSearch(){
        // 构造查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withQuery(QueryBuilders.matchAllQuery());
        queryBuilder.withSort(SortBuilders.fieldSort("age").order(SortOrder.DESC));
        Page<DocBean> search = esRepository.search(queryBuilder.build());
        search.forEach(student -> {
            log.info("[student]={}",student);
        });
    }

}
