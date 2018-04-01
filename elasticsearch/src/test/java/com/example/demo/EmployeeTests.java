package com.example.demo;


import com.example.demo.entity.Employee;
import com.example.demo.service.JestService;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.SearchResult;
import io.searchbox.core.search.aggregation.TermsAggregation;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * https://github.com/searchbox-io/Jest/tree/master/jest
 * <p>
 * 支持包含多值标签、数值、以及全文本的数据
 * 检索任一雇员的完整信息
 * 允许结构化搜索，比如查询 30 岁以上的员工
 * 允许简单的全文搜索以及较复杂的短语搜索
 * 支持在匹配文档内容中高亮显示搜索片段
 * 支持基于数据创建和管理分析仪表盘
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeTests {

    @Autowired
    private JestClient jestClient;

    @Autowired
    private JestService jestService;

    private String indexName = "megacorp";
    private String typeName = "employee";

    @Test
    public void createIndex() throws Exception {
        boolean result = jestService.createIndex(indexName);
        System.out.println(result);
    }

    @Test
    public void index() throws Exception {

        List<Object> objs = new ArrayList<Object>();
        objs.add(new Employee());
        objs.add(new Employee());
        boolean result = jestService.index(indexName, typeName, objs);
        System.out.println(result);
    }

    //轻量搜索
    @Test
    public void matchQuery() throws Exception {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        QueryBuilder queryBuilder = QueryBuilders
                .matchQuery("lastName", "Smith");//单值完全匹配查询
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.size(10);
        searchSourceBuilder.from(0);
        String query = searchSourceBuilder.toString();
        System.out.println(query);
        SearchResult result = jestService.search(indexName, typeName, query);
        List<SearchResult.Hit<Employee, Void>> hits = result.getHits(Employee.class);
        System.out.println("Size:" + hits.size());
        for (SearchResult.Hit<Employee, Void> hit : hits) {
            Employee employee = hit.source;
            System.out.println(employee.toString());
        }
    }

    //复杂查询
    @Test
    public void matchQueryAndFilter() throws Exception {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        QueryBuilder queryBuilder = QueryBuilders
                .matchQuery("lastName", "Smith");//单值完全匹配查询
        QueryBuilder filter = QueryBuilders
                .rangeQuery("age")
                .gte(30)
                .lte(50)
                .includeLower(true)
                .includeUpper(true);//区间查询
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.postFilter(filter);
        searchSourceBuilder.size(10);
        searchSourceBuilder.from(0);
        String query = searchSourceBuilder.toString();
        System.out.println(query);
        SearchResult result = jestService.search(indexName, typeName, query);
        List<SearchResult.Hit<Employee, Void>> hits = result.getHits(Employee.class);
        System.out.println("Size:" + hits.size());
        for (SearchResult.Hit<Employee, Void> hit : hits) {
            Employee employee = hit.source;
            System.out.println(employee.toString());
        }
    }

    //全文搜索
    @Test
    public void matchQueryFullText() throws Exception {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        QueryBuilder queryBuilder = QueryBuilders
                .matchQuery("about", "rock climbing");//单值完全匹配查询
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.size(10);
        searchSourceBuilder.from(0);
        String query = searchSourceBuilder.toString();
        System.out.println(query);
        SearchResult result = jestService.search(indexName, typeName, query);
        List<SearchResult.Hit<Employee, Void>> hits = result.getHits(Employee.class);
        System.out.println("Size:" + hits.size());
        for (SearchResult.Hit<Employee, Void> hit : hits) {
            Employee employee = hit.source;
            System.out.println(employee.toString());
        }
    }

    //短语搜索
    @Test
    public void matchPhrase() throws Exception {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        QueryBuilder queryBuilder = QueryBuilders
                .matchPhraseQuery("about", "rock climbing");//单值完全匹配查询
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.size(10);
        searchSourceBuilder.from(0);
        String query = searchSourceBuilder.toString();
        System.out.println(query);
        SearchResult result = jestService.search(indexName, typeName, query);
        List<SearchResult.Hit<Employee, Void>> hits = result.getHits(Employee.class);
        System.out.println("Size:" + hits.size());
        for (SearchResult.Hit<Employee, Void> hit : hits) {
            Employee employee = hit.source;
            System.out.println(employee.toString());
        }
    }

    //高亮搜索
    @Test
    public void matchPhraseHignLight() throws Exception {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        QueryBuilder queryBuilder = QueryBuilders
                .matchPhraseQuery("about", "rock climbing");//单值完全匹配查询
        searchSourceBuilder.query(queryBuilder);
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("about");//高亮field
        highlightBuilder.preTags("<em>").postTags("</em>");//高亮标签
        highlightBuilder.fragmentSize(200);//高亮内容长度
        searchSourceBuilder.highlighter(highlightBuilder);
        searchSourceBuilder.size(10);
        searchSourceBuilder.from(0);
        String query = searchSourceBuilder.toString();
        System.out.println(query);
        SearchResult result = jestService.search(indexName, typeName, query);
        List<SearchResult.Hit<Employee, Void>> hits = result.getHits(Employee.class);
        System.out.println("Size:" + hits.size());
        for (SearchResult.Hit<Employee, Void> hit : hits) {
            Employee employee = hit.source;
            System.out.println(employee.toString());
            System.out.println(hit.highlight);
        }
    }

    @Test
    public void testJson() throws Exception {
        String query = "{\n" +
                "  \"aggs\": {\n" +
                "    \"all_interests\": {\n" +
                "      \"terms\": { \"field\": \"interests.keyword\" }\n" +
                "    }\n" +
                "  }\n" +
                "}";

        System.out.println(query);
        SearchResult result = jestService.search(indexName, typeName, query);
        System.out.println(result.getJsonString());

//        List<TermsAggregation.Entry> agg =
//                result.getAggregations().getTermsAggregation("all_interests").getBuckets();
//        for (TermsAggregation.Entry entry : agg) {
//            System.out.println(entry.getKey() + " " + entry.getCount() + " " + entry.getAvgAggregation("avg_age").getAvg());
//        }


    }

    //分析 挖掘出雇员中最受欢迎的兴趣爱好
    @Test
    public void testAgg() throws Exception {
        QueryBuilder queryBuilder = QueryBuilders
                .matchQuery("lastName", "Smith");//单值完全匹配查询
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        AggregationBuilder aggregationBuilder =
                AggregationBuilders.terms("all_interests").field("interests.keyword")
                                .subAggregation(AggregationBuilders.avg("avg_age").field("age"));
//                                .subAggregation(AggregationBuilders.count("totalNum").field("name.keyword"))); //4

        searchSourceBuilder.aggregation(aggregationBuilder);
        searchSourceBuilder.query(queryBuilder);
        String query = searchSourceBuilder.toString();
        System.out.println(query);
        SearchResult result = jestService.search(indexName, typeName, query);

        List<TermsAggregation.Entry> agg =
                result.getAggregations().getTermsAggregation("all_interests").getBuckets();
        for (TermsAggregation.Entry entry : agg) {
            System.out.println(entry.getKey() + " " + entry.getCount() + " " + entry.getAvgAggregation("avg_age").getAvg());
        }

    }
    //组合查询
    @Test
    public void testAnd() throws Exception {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.rangeQuery("age") //对time字段进行范围限定
                        .gte("30").lt(50))
//也可用from("1510568631869").to("1511166160231");
                .must(QueryBuilders.matchQuery("lastName", "Smith"));

        searchSourceBuilder.query(queryBuilder);
        String query = searchSourceBuilder.toString();
        System.out.println(query);
        SearchResult result = jestService.search(indexName, typeName, query);
        List<SearchResult.Hit<Employee, Void>> hits = result.getHits(Employee.class);
        System.out.println("Size:" + hits.size());
        for (SearchResult.Hit<Employee, Void> hit : hits) {
            Employee employee = hit.source;
            System.out.println(employee.toString());
            System.out.println(result.getJsonString());

        }
    }

    //所有
    @Test
    public void testSearchAll() {
        List<SearchResult.Hit<Employee, Void>> hits = jestService.searchAll(indexName, new Employee());
        System.out.println("Size:" + hits.size());
        for (SearchResult.Hit<Employee, Void> hit : hits) {
            Employee employee = hit.source;
            System.out.println(employee.toString());

        }
    }

//    @Test
//    public void testSearchScroll() {
//        SearchScroll scroll = new SearchScroll.Builder(scrollId, "1m").build();
//        JestResult result = jestClient.execute(scroll);
//    }

    @Test
    public void testSearchAllByKeyword() {
        List<SearchResult.Hit<Employee, Void>> hits = jestService.createSearch(indexName,"Smith", new Employee(), "lastName");
        System.out.println("Size:" + hits.size());
        for (SearchResult.Hit<Employee, Void> hit : hits) {
            Employee employee = hit.source;
            System.out.println(employee.toString());
            System.out.println(hit.highlight);
        }
    }

    @Test
    public void termQuery() throws Exception {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        QueryBuilder queryBuilder = QueryBuilders
                .termQuery("lastName", "Smith");//单值完全匹配查询
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.size(10);
        searchSourceBuilder.from(0);
        String query = searchSourceBuilder.toString();
        System.out.println(query);
        SearchResult result = jestService.search(indexName, typeName, query);
        List<SearchResult.Hit<Employee, Void>> hits = result.getHits(Employee.class);
        System.out.println("Size:" + hits.size());
        for (SearchResult.Hit<Employee, Void> hit : hits) {
            Employee Employee = hit.source;
            System.out.println(Employee.toString());
        }
    }

    @Test
    public void termsQuery() throws Exception {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        QueryBuilder queryBuilder = QueryBuilders
                .termsQuery("name", new String[]{ "T:o\"m-", "J,e{r}r;y:" });//多值完全匹配查询
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.size(10);
        searchSourceBuilder.from(0);
        String query = searchSourceBuilder.toString();
        System.out.println(query);
        SearchResult result = jestService.search(indexName, typeName, query);
        List<SearchResult.Hit<Employee, Void>> hits = result.getHits(Employee.class);
        System.out.println("Size:" + hits.size());
        for (SearchResult.Hit<Employee, Void> hit : hits) {
            Employee Employee = hit.source;
            System.out.println(Employee.toString());
        }
    }

    @Test
    public void wildcardQuery() throws Exception {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        QueryBuilder queryBuilder = QueryBuilders
                .wildcardQuery("name", "*:*");//通配符和正则表达式查询
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.size(10);
        searchSourceBuilder.from(0);
        String query = searchSourceBuilder.toString();
        System.out.println(query);
        SearchResult result = jestService.search(indexName, typeName, query);
        List<SearchResult.Hit<Employee, Void>> hits = result.getHits(Employee.class);
        System.out.println("Size:" + hits.size());
        for (SearchResult.Hit<Employee, Void> hit : hits) {
            Employee Employee = hit.source;
            System.out.println(Employee.toString());
        }
    }

    @Test
    public void prefixQuery() throws Exception {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        QueryBuilder queryBuilder = QueryBuilders
                .prefixQuery("name", "T:o");//前缀查询
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.size(10);
        searchSourceBuilder.from(0);
        String query = searchSourceBuilder.toString();
        System.out.println(query);
        SearchResult result = jestService.search(indexName, typeName, query);
        List<SearchResult.Hit<Employee, Void>> hits = result.getHits(Employee.class);
        System.out.println("Size:" + hits.size());
        for (SearchResult.Hit<Employee, Void> hit : hits) {
            Employee Employee = hit.source;
            System.out.println(Employee.toString());
        }
    }

    @Test
    public void rangeQuery() throws Exception {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        QueryBuilder queryBuilder2 = QueryBuilders
                .rangeQuery("age")
                .gte(30)
                .lte(50)
                .includeLower(true)
                .includeUpper(true);//区间查询
        searchSourceBuilder.query(queryBuilder2);
        searchSourceBuilder.size(10);
        searchSourceBuilder.from(0);
        String query = searchSourceBuilder.toString();
        System.out.println(query);
        SearchResult result = jestService.search(indexName, typeName, query);
        List<SearchResult.Hit<Employee, Void>> hits = result.getHits(Employee.class);
        System.out.println("Size:" + hits.size());
        for (SearchResult.Hit<Employee, Void> hit : hits) {
            Employee Employee = hit.source;
            System.out.println(Employee.toString());
        }
    }

    @Test
    public void queryString() throws Exception {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        QueryBuilder queryBuilder = QueryBuilders.queryStringQuery(QueryParser.escape("T:o\""));//文本检索，应该是将查询的词先分成词库中存在的词，然后分别去检索，存在任一存在的词即返回，查询词分词后是OR的关系。需要转义特殊字符
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.size(10);
        searchSourceBuilder.from(0);
        String query = searchSourceBuilder.toString();
        System.out.println(query);
        SearchResult result = jestService.search(indexName, typeName, query);
        List<SearchResult.Hit<Employee, Void>> hits = result.getHits(Employee.class);
        System.out.println("Size:" + hits.size());
        for (SearchResult.Hit<Employee, Void> hit : hits) {
            Employee Employee = hit.source;
            System.out.println(Employee.toString());
        }
    }

    @Test
    public void count() throws Exception {

        String[] name = new String[]{ "T:o\"m-", "Jerry" };
        String from = "2016-09-01T00:00:00";
        String to = "2016-10-01T00:00:00";
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.termsQuery("name", name))
                .must(QueryBuilders.rangeQuery("birth").gte(from).lte(to));
        searchSourceBuilder.query(queryBuilder);
        String query = searchSourceBuilder.toString();
        System.out.println(query);
        Double count = jestService.count(indexName, typeName, query);
        System.out.println("Count:" + count);
    }

    @Test
    public void get() throws Exception {

        String id = "2";
        JestResult result = jestService.get(indexName, typeName, id);
        if (result.isSucceeded()) {
            Employee Employee = result.getSourceAsObject(Employee.class);
            System.out.println(Employee.toString());
        }
    }

    @Test
    public void deleteIndexDocument() throws Exception {

        String id = "2";
        boolean result = jestService.delete(indexName, typeName, id);
        System.out.println(result);
    }

    @Test
    public void deleteIndex() throws Exception {

        boolean result = jestService.delete(indexName);
        System.out.println(result);
    }

}










