package com.example.demo;


import com.example.demo.entity.Article;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.client.JestResultHandler;
import io.searchbox.cluster.Health;
import io.searchbox.cluster.NodesInfo;
import io.searchbox.cluster.NodesStats;
import io.searchbox.core.*;
import io.searchbox.indices.ClearCache;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.IndicesExists;
import org.elasticsearch.common.settings.Settings;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * https://github.com/searchbox-io/Jest/tree/master/jest
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticsearchApplicationTests {

    @Autowired
    private JestClient client;



    @Test
    public void  testCreateIndex() throws Exception {
        Settings.Builder settingsBuilder = Settings.builder();
        settingsBuilder.put("number_of_shards",5);
        settingsBuilder.put("number_of_replicas",1);

        client.execute(new CreateIndex.Builder("articles").settings(settingsBuilder.build().getAsMap()).build());
    }

    //清除缓存
    @Test
    public void clearCache() throws Exception {
        ClearCache clearCache = new ClearCache.Builder().build();
        JestResult jestResult = client.execute(clearCache);
        assert (jestResult.isSucceeded() == true);
    }

    //索引存在
    @Test
    public void testIndicesExists() throws Exception {
        IndicesExists indicesExists = new IndicesExists.Builder("articles").build();
        JestResult jestResult = client.execute(indicesExists);
        System.out.println(jestResult.getJsonString());
        assert (jestResult.isSucceeded() == true);
    }


    @Test
    public void testNodesInfo() throws Exception {
        NodesInfo nodesInfo = new NodesInfo.Builder().build();
        JestResult jestResult = client.execute(nodesInfo);
        System.out.println(jestResult.getJsonString());
        assert (jestResult.isSucceeded() == true);
    }

    @Test
    public void testNodesStats() throws Exception {
        NodesStats nodesStats = new NodesStats.Builder().build();
        JestResult jestResult = client.execute(nodesStats);
        System.out.println(jestResult.getJsonString());
        assert (jestResult.isSucceeded() == true);
    }

    @Test
    public void testHealth() throws Exception {
        Health health = new Health.Builder().build();
        JestResult jestResult = client.execute(health);
        System.out.println(jestResult.getJsonString());
        assert (jestResult.isSucceeded() == true);
    }


    @Test
    public void testDelete() throws Exception {
        JestResult result = client.execute(new Delete.Builder("0")
                .index("twitter")
                .type("tweet")
                .build());

        assert (result.isSucceeded() == true);
    }

    @Test
    public void testDeleteByQuery() throws Exception {
        JestResult result = client.execute(new DeleteByQuery.Builder("0")
                .addIndex("twitter")
                .addType("tweet")
                .build());

        assert (result.isSucceeded() == true);
    }



    /**
     * 第一次添加，id存在的修改
     * @throws Exception
     */
    @Test
    public void testPOJO() throws Exception {
        Article source = new Article();
        source.setAuthor("John Ronald Reuel Tolkien");
        source.setContent("The Lord of the Rings is an epic high fantasy novel");
        source.setUrl("http://localhost:9100/1/2/3");
        source.setAuthor("kimchy");

        Index index = new Index.Builder(source).index("twitter").type("tweet").id("1").build();
        JestResult jestResult= client.execute(index);
        assert (jestResult.isSucceeded() == true);
    }

    @Test
    public void testGet() throws Exception{
        Get get = new Get.Builder("twitter", "2").type("tweet").build();
        JestResult result = client.execute(get);
        Article article = result.getSourceAsObject(Article.class);
        System.out.println(article.getUrl());
        assert (result.isSucceeded() == true);
    }

    //

    @Test
    public void testUpdate() throws Exception {
        JestResult result = client.execute(new Update.Builder("0")
                .index("twitter")
                .type("tweet")
                .build());

        assert (result.isSucceeded() == true);
    }

    @Test
    public void testBulk() throws Exception {
        Article article1 = new Article(4);
        Article article2 = new Article(5);

        Bulk bulk = new Bulk.Builder()
                .defaultIndex("twitter")
                .defaultType("tweet")
                .addAction(new Index.Builder(article1).build())
                .addAction(new Index.Builder(article2).build())
                .addAction(new Delete.Builder("1").index("twitter").type("tweet").build())
                .build();

        JestResult jestResult = client.execute(bulk);
        assert (jestResult.isSucceeded() == true);
    }

    @Test
    public void testAsyncGet() throws Exception{
        Get get = new Get.Builder("twitter", "1").type("tweet").build();
//        JestResult result = client.execute(get);

        client.executeAsync(get,new JestResultHandler<JestResult>() {
            @Override
            public void completed(JestResult result) {
                Article article = result.getSourceAsObject(Article.class);
                System.out.println(article.getUrl());
                assert (result.isSucceeded() == true);
            }
            @Override
            public void failed(Exception ex) {
                ex.printStackTrace();
            }
        });

        System.out.println("1111111111111");

        Thread.sleep(10000);


    }




}
