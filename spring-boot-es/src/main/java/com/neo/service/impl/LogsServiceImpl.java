package com.neo.service.impl;

import com.neo.Repository.LogsRepostitory;
import com.neo.entity.LogsIndex;
import com.neo.service.LogsService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Company
 * @Discription
 * @Author guoxiaojing
 * @CreateDate 2018/3/9 10:33
 * @Version 1.0
 */
@Service
public class LogsServiceImpl implements LogsService{

    @Autowired
    private LogsRepostitory logsRepostitory;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public LogsIndex saveLog() {
        LogsIndex logsIndex = new LogsIndex();
        logsIndex.setId(123l);
        logsIndex.setCount(100);
        logsIndex.setTitle("这是标题3");
        logsIndex.setCreateTime("2014-12-11 12:12:12");
        logsIndex.setEssence(true);
        logsIndex.setState(0);
        logsRepostitory.save(logsIndex);
        LogsIndex index = logsRepostitory.save(logsIndex);
        return index;
    }


    /**
     * 条件查询
     */
    public List<LogsIndex> search() {
      /*创建boolQuery 构造了一个组合查询 可以通过must、should、mustNot方法对QueryBuilder进行组合，形成多条件查询*/
        BoolQueryBuilder qb = QueryBuilders.boolQuery();

        /**
         * 字段匹配
         *
         *  QueryBuilders.matchQuery()  matchQuery()能够使用某一field的值对Document进行查询 匹配查询
         *  QueryBuilders.termQuery()   termQuery() 不会进行分词查询
         *  QueryBuilders.rangeQuery()   rangeQuery()字段过滤查询
         *  QueryBuilders.multiMatchQuery() 相对于matchQuery，multiMatchQuery针对的是多个field
         *      当multiMatchQuery中，fieldNames参数只有一个时，其作用与matchQuery相当
         *      而当fieldNames有多个参数时，如field1和field2，那查询的结果中，要么field1中包含text，要么field2中包含text
         *  QueryBuilders.matchPhraseQuery()   短语的形式查询
         *  QueryBuilders.matchPhrasePrefixQuery()   短语的形式查询 同matchPhraseQuery()  英文有区别
         *
         */

        qb.must(QueryBuilders.matchQuery("字段", "你好吗11"));
        qb.should(QueryBuilders.matchQuery("字段", "好人 "));
        qb.mustNot(QueryBuilders.multiMatchQuery("好人", "字段", "字段"));
        //区间
        qb.must(QueryBuilders.rangeQuery("time").from("2510641095408").to("2510641234949"));

        SearchQuery searchQuery = new NativeSearchQuery(qb);

        //要显示的字段
        searchQuery.addFields("id", "keyword", "title", "abstracts");
        //排序的字段
        searchQuery.addSort(new Sort(Sort.Direction.ASC, "id"));
        //过滤
        searchQuery.addSourceFilter(new FetchSourceFilter(null, null));
        //分页
        searchQuery.setPageable(new PageRequest(0, 50));

        List<LogsIndex> content = logsRepostitory.search(searchQuery).getContent();

        return content;
    }
}
