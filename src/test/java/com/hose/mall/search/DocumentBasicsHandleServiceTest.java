package com.hose.mall.search;

import com.google.common.collect.Lists;
import com.hose.mall.search.index.DocumentBasicsHandleService;
import com.hose.mall.util.mapper.JsonUtils;
import com.hose.mall.util.string.StaticsStringNameUtils;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author wangmi wangmi@hosecloud.com
 * @version 1.0
 * @Copyright (c) 合思技术团队 https://www.ekuaibao.com/
 * @Package com.hose.mall.search
 * @Project mall-tool
 * @date 2020/4/26 10:30 上午
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DocumentBasicsHandleServiceTest {
    @Autowired
    private DocumentBasicsHandleService documentBasicsHandleService;
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    protected static final String PREFERENCE = "_primary_first";
    private static final JsonUtils JSON = JsonUtils.nonNullMapper();
    private String indexName="hotel_v1_index";
    private String alias="hotel_index";
    private String indexType="hotel";

    @Test
    public void find(){
        double min = 100.0;
        double max = 500.0;
        double boundedDouble = min + new Random().nextDouble() * (max - min);
        System.out.println(boundedDouble);
    }

    @Test
    public void batchSave(){
        Map<String, String> datas=new HashMap<String, String>();
        for (int i=0;i<1000000;i++){
            HotelVo hotelVo=new HotelVo();
            hotelVo.setHotelNo("hotel_"+i);
            hotelVo.setNameCn("美豪酒店深圳机场店"+i);
            hotelVo.setGeoId("100"+(i%100));
            hotelVo.setThemes((i%10)+"");
            hotelVo.setCategory(i%5);
            hotelVo.setStarRate(i%5);
            hotelVo.setLatitude(new Random(400).nextDouble());
            hotelVo.setLongitude(new Random(400).nextDouble());
            double min = 100.0;
            double max = 500.0;
            double boundedDouble = min + new Random().nextDouble() * (max - min);
            hotelVo.setLowRate(boundedDouble);
            hotelVo.setHotelStatus("OPEN");
            DetailAdapterVo adapterVo=new DetailAdapterVo();
            adapterVo.setHotelNo(hotelVo.getHotelNo());
            adapterVo.setHotelPltNo("elong_"+i);
            adapterVo.setLowRate(hotelVo.getLowRate());
            adapterVo.setPltGeo("0101");
            adapterVo.setPlatform("ELONG");
            adapterVo.setPltStatus(true);
            hotelVo.setAdapters(Lists.newArrayList(adapterVo));
            hotelVo.setRoomTotal(new Random(200).nextInt()+"");
            hotelVo.setPhone("0755-2328988"+(i%10));
            hotelVo.setCoverImage("http://pavo.elongstatic.com/i/Hotel870_470/nw_FsMuNnQgiQ.webp");
            hotelVo.setTraffic("美豪酒店(深圳机场店)位于深圳宝安区宝安大道，近凯成二路，距离罗宝地铁线后瑞站A出口约300米，距离福永码头约8分钟车程，经机场或福永码头可转香港、澳门。酒店时尚又舒适的客房内配有先进的设施设备。入住期间酒店还提供免押金、免查房等快速入离服务，提供免费上下午茶，免费洗衣、熨衣，30分钟不满意全额退房，免费延迟退房至14:00，免费机场接送机等超值服务（详情请咨询商家）。酒店精心为您打造特色的贴身管家订制服务，不断为您创造再次选择美豪的理由。致力于为客人创造无限尊崇礼遇及舒适的入住体验，将美豪这一酒店品牌在城市间梦幻般绽放，自在旅途，畅想商务，人在旅途，心在美豪！美豪酒店--献给懂生活的您！酒店以热忱真挚的服务，为宾客营造舒心、静谧的入住环境，使宾客享受每一段自在旅程。\n");
            hotelVo.setAddressCn("宝安大道"+i+"号(地铁1号线后瑞地铁站A出口)");
            datas.put("hotel_"+i,JSON.toJson(hotelVo));
            if(i%5000==0 && i>0){
                System.out.println("-----------------------------------"+i);
                documentBasicsHandleService.batchSave(indexName,datas);
                datas=new HashMap<String, String>();
            }
        }
        documentBasicsHandleService.batchSave(indexName,datas);
    }



    @Test
    public void hotelSearch(){
        SearchRequest request = new SearchRequest(alias);
        request.searchType(SearchType.DFS_QUERY_THEN_FETCH);
//        request.types(indexType);

        SearchSourceBuilder searchSource = new SearchSourceBuilder();
        searchSource.fetchSource(new String[]{"hotelNo","geoId","nameCn","addressCn","phone","category","themes","lowRate","adapters"}, null);
        searchSource.explain(false);
        searchSource.timeout(new TimeValue(6, TimeUnit.SECONDS));


        BoolQueryBuilder bool = QueryBuilders.boolQuery();
        BoolQueryBuilder filter = QueryBuilders.boolQuery();
        buildQueryConditionAndFilter(bool, filter);
        FunctionScoreQueryBuilder functionScore = buildFunctionScore(bool);
        searchSource.query(functionScore);
        buildSort("PA", searchSource);
        searchSource.postFilter(filter);
        searchSource.from(0);
        searchSource.size(10);

        request.source(searchSource);
        System.out.println(searchSource.toString());
        // 请求响应
        try {
            SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
            // 根据状态和数据条数验证是否返回了数据
            if (RestStatus.OK.equals(response.status()) && response.getHits().getTotalHits().value> 0) {
                SearchHits hits = response.getHits();
                for (SearchHit hit : hits) {
                    // 输出查询信息
                    log.info(hit.getSourceAsString());
                }
            }
            log.info("response {} request {}", response, request.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private FunctionScoreQueryBuilder buildFunctionScore(BoolQueryBuilder bool) {
        FunctionScoreQueryBuilder functionScore = null;
        String sort ="PA";
        if ("PA".equals(sort)) {
            sort = "1/doc['lowRate'].value";
        } else if ("PD".equals(sort)) {
            sort = "doc['lowRate'].value";
        } else if ("D".equals(sort)) {
            sort = "doc['sortScore'].value";
        }
        if (sort != null) {
            FunctionScoreQueryBuilder.FilterFunctionBuilder[] functions = {new FunctionScoreQueryBuilder.FilterFunctionBuilder(ScoreFunctionBuilders.scriptFunction("return doc['lowRate'].value<=0 ? -1.0:"+ sort))};
            functionScore = QueryBuilders.functionScoreQuery(bool, functions);
        } else {
            functionScore = QueryBuilders.functionScoreQuery(bool);
        }
        functionScore.scoreMode(FunctionScoreQuery.ScoreMode.SUM);
        functionScore.boostMode(CombineFunction.MULTIPLY);
        return functionScore;
    }

    private static void buildSort(String sort,SearchSourceBuilder searchSource) {
        // 排序方式 DD默认推荐星级倒序 RA价格升序 RD价格降序 LA距离升序
        if ("PA".equals(sort)) {
            searchSource.sort("_score", SortOrder.DESC);
            searchSource.sort("lowRate", SortOrder.ASC);
        } else if ("PD".equals(sort)) {
            searchSource.sort("lowRate", SortOrder.DESC);
        } else {
            searchSource.sort("_score", SortOrder.DESC);
            searchSource.sort(SortBuilders.fieldSort("sortScore").order(SortOrder.DESC));
        }
    }

    private void buildQueryConditionAndFilter(BoolQueryBuilder bool, BoolQueryBuilder filter) {
        //开通渠道产品
        BoolQueryBuilder platform = QueryBuilders.boolQuery();
        BoolQueryBuilder boolc = QueryBuilders.boolQuery();
        BoolQueryBuilder c = platformProductQuery();
        platform.should(c);
        if (platform.hasClauses()) {
            NestedQueryBuilder hquery = QueryBuilders.nestedQuery("adapters", platform, org.apache.lucene.search.join.ScoreMode.Total);
            boolc.should(hquery);
        }
        if (boolc.hasClauses()) {
            filter.must(boolc);
        }
        bool.must(QueryBuilders.termsQuery("geoId","10013").boost(0.01F));
        String text = StaticsStringNameUtils.stringFilter("美豪酒店深圳机场店");
        bool.must(QueryBuilders.multiMatchQuery(text,"nameCn","addressCn").fuzziness("AUTO").slop(1).minimumShouldMatch("90%").boost(0.2F));
//        bool.must(QueryBuilders.rangeQuery("lowRate").from(250).to(350));

        filter.must(QueryBuilders.termQuery("hotelStatus", "OPEN"));
//        // 去除0的以农家乐为主要的
        filter.must(QueryBuilders.rangeQuery("category").from(1));
//
        filter.must(QueryBuilders.termsQuery("themes","3"));
    }

    private  BoolQueryBuilder platformProductQuery() {
        BoolQueryBuilder c = QueryBuilders.boolQuery();
        c.must(QueryBuilders.termQuery("adapters.platform", "ELONG"));
        c.must(QueryBuilders.termQuery("adapters.pltStatus", true));
        return c;
    }
}
