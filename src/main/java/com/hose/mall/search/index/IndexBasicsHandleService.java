package com.hose.mall.search.index;

import com.hose.mall.search.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author wangmi wangmi@hosecloud.com
 * @version 1.0
 * @Copyright (c) 合思技术团队 https://www.ekuaibao.com/
 * @Package com.hose.mall.search.index
 * @Project mall-tool
 * @date 2020/4/26 12:49 上午
 */
@Component("indexBasicsHandleService")
@Slf4j
public class IndexBasicsHandleService extends  DocumentBasicsHandleService{

    /**
     * 创建索引
     * @param indexName
     * @param type
     * @return
     */
    public boolean createIndex(String indexName,String type) {
        boolean isCreated =false;
        try {
            String source = FileUtil.readJsonDefn("mappings/"+type+".json");
            // 创建索引配置信息，配置
            Settings settings = Settings.builder()
                    .put("index.number_of_shards", 3)
                    .put("index.number_of_replicas", 1)
                    .put("index.max_result_window", 30000)
                    .put("index.refresh_interval","3s")
                    .build();
            // 新建创建索引请求对象，然后设置索引类型（ES 7.0 将不存在索引类型）和 mapping 与 index 配置
            CreateIndexRequest request = new CreateIndexRequest(indexName);
            request.settings(settings);
            request.mapping(source,XContentType.JSON);
            // RestHighLevelClient 执行创建索引
            CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(request,RequestOptions.DEFAULT);
            // 判断是否创建成功
            isCreated = createIndexResponse.isAcknowledged();
            log.info("isCreated：{}", isCreated);
        } catch (IOException e) {
            log.error("createIndex fail", e);
        }
        return isCreated;
    }

    /**
     * 删除索引
     * @param indexName
     * @return
     */
    public boolean deleteIndex(String indexName) {
        boolean isDeleted =false;
        try {
            // 新建删除索引请求对象
            DeleteIndexRequest request = new DeleteIndexRequest(indexName);
            // 执行删除索引
            AcknowledgedResponse acknowledgedResponse = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
            // 判断是否删除成功
            isDeleted = acknowledgedResponse.isAcknowledged();
            log.info("isDeleted：{}", isDeleted);
        } catch (IOException e) {
            log.error("deleteIndex fail", e);
        }
        return isDeleted;
    }

    /**
     * 索引是否存在
     * @param indexName
     * @return
     */
    public boolean isIndexExist(String indexName) {
        boolean isexists=false;
        try {
            GetIndexRequest request = new GetIndexRequest(indexName);
            isexists=restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
            log.info("是否存在：{}", isexists);
        } catch (IOException e) {
            log.error("isexists fail", e);
        }
        return isexists;
    }

    /**
     * 索引添加别名
     * @param indexName
     * @param alias
     * @return
     */
    public boolean addIndexAlias(String indexName,String alias) {
        boolean isAdded =false;
        try {
            IndicesAliasesRequest request = new IndicesAliasesRequest();
            IndicesAliasesRequest.AliasActions aliasAction =
                    new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.ADD)
                            .index(indexName)
                            .alias(alias);
            request.addAliasAction(aliasAction);
            AcknowledgedResponse createIndexAliasesResponse = restHighLevelClient.indices().updateAliases(request, RequestOptions.DEFAULT);
            // 是否添加成功
            isAdded = createIndexAliasesResponse.isAcknowledged();
            log.info("isAdded：{}", isAdded);
        } catch (IOException e) {
            log.error("addIndexAlias fail", e);
        }
        return isAdded;
    }


    /**
     * 索引删除别名
     * @param indexName
     * @param alias
     * @return
     */
    public boolean deleteAlias(String indexName,String alias) {
        boolean isDeleted =false;
        try {
            IndicesAliasesRequest request = new IndicesAliasesRequest();
            IndicesAliasesRequest.AliasActions removeAction =
                    new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.REMOVE)
                            .index(indexName)
                            .alias(alias);
            request.addAliasAction(removeAction);
            AcknowledgedResponse createIndexAliasesResponse = restHighLevelClient.indices().updateAliases(request, RequestOptions.DEFAULT);
            // 是否添加成功
            isDeleted = createIndexAliasesResponse.isAcknowledged();
            log.info("deleteAlias ：{}", isDeleted);
        } catch (IOException e) {
            log.error("deleteAlias fail", e);
        }
        return isDeleted;
    }

    /**
     * 别名是否存在
     * @param indexName
     * @param alias
     * @return
     */
    public boolean isAliasExist(String indexName,String alias) {
        boolean isexists=false;
        try {
            GetAliasesRequest request = new GetAliasesRequest();
            request.indices(indexName);
            request.aliases(alias);
            isexists = restHighLevelClient.indices().existsAlias(request, RequestOptions.DEFAULT);
            log.info("isAliasExist：{}", isexists);
        } catch (IOException e) {
            log.error("isAliasExist fail", e);
        }
        return isexists;
    }

}
