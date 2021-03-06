package com.neo.Repository;


import com.neo.entity.LogsIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by dongyaofeng on 2017/12/25.
 */
public interface LogsRepostitory extends ElasticsearchRepository<LogsIndex, Long> {

//    long countByTitle(String title);

//    List findByClickCountBetween(Long star, Long end);

    /**
     * findByName
     * findByNameAndPrice  And
     * findByNameOrPrice  or
     * findByPriceBetween   Between
     * findByNameIn(Collection<String>names)
     * findByNameLike
     * findByPriceAfter
     * <p>
     * findByLastnameOrderByFirstnameAsc(String lastname);
     * https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/   API 查看
     */
//    List<LogsIndex> findByKeywordIn(List<String> name);

    //关键字... ,或者 点击> * , 或者 描述.....
//    List<LogsIndex> findByKeywordAndClickCountBetweenOrderByIdDesc(String keyword, Long star, Long end);

}
