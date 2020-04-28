package com.hose.mall.search.index;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * @author wangmi wangmi@hosecloud.com
 * @version 1.0
 * @Copyright (c) 合思技术团队 https://www.ekuaibao.com/
 * @Package com.hose.mall.search.index
 * @Project mall-tool
 * @date 2020/4/26 3:35 上午
 */
@Component("documentBasicsHandleService")
@Slf4j
public class DocumentBasicsHandleService {
    private static Logger logger= LogManager.getLogger(DocumentBasicsHandleService.class);
    @Autowired
    protected RestHighLevelClient restHighLevelClient;

    /**
     * 添加文档
     * @param indexName
     * @param indexType
     * @param id
     * @param data
     * @return
     */
    public boolean addDocument(String indexName,String id,String data){
        boolean isSuccess =false;
        try{
            IndexRequest request = new IndexRequest(indexName);
            request.id(id);
            request.source(data, XContentType.JSON);
            IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);
            if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
                isSuccess=true;
            }
        }catch (IOException e){
            log.error("addDocument fail", e);
        }
        return isSuccess;
    }

    /**
     * 修改文档
     * @param indexName
     * @param indexType
     * @param id
     * @param data
     * @return
     */
    public boolean updateDocument(String indexName, String id,Map<String, String> data) throws IOException {
        boolean isSuccess =false;
        try{
            UpdateRequest request = new UpdateRequest(indexName,id);
            request.doc(data, XContentType.JSON);
            UpdateResponse updateResponse = restHighLevelClient.update(request, RequestOptions.DEFAULT);
            if (updateResponse.getResult() == DocWriteResponse.Result.UPDATED) {
                isSuccess=true;
            }
        }catch (IOException e){
            log.error("updateDocument fail", e);
        }
        return isSuccess;
    }

    /**
     * 删除文档
     * @param indexName
     * @param indexType
     * @param id
     * @return
     */
    public boolean deleteDocument(String indexName,String id){
        boolean isSuccess =false;
        try{
            DeleteRequest request = new DeleteRequest(indexName,id);
            DeleteResponse deleteResponse = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
            if (deleteResponse.getResult() == DocWriteResponse.Result.DELETED) {
                isSuccess=true;
            }
        }catch (IOException e){
            log.error("deleteDocument fail", e);
        }
        return isSuccess;
    }


    /**
     * 判断文档是否存在
     * @param indexName
     * @param indexType
     * @param id
     * @return
     */
    public boolean existsDocument(String indexName,String id){
        boolean isSuccess =false;
        try{
            GetRequest getRequest = new GetRequest(indexName,id);
            // 是否获取源码内容
            getRequest.fetchSourceContext(new FetchSourceContext(false));
            isSuccess= restHighLevelClient.exists(getRequest, RequestOptions.DEFAULT);
        }catch (IOException e){
            log.error("existsDocument fail", e);
        }
        return isSuccess;
    }

    /**
     * 查询文档
     * @param indexName
     * @param indexType
     * @param id
     * @return
     */
    public String findDocument(String indexName,String id){
        String doc =null;
        try{
            GetRequest request = new GetRequest(indexName, id);
            GetResponse response = restHighLevelClient.get(request, RequestOptions.DEFAULT);
            doc = response.getSourceAsString();
        }catch (IOException e){
            log.error("findDocument fail", e);
        }
        return doc;
    }


    /**
     * 查询文档
     * @param indexName
     * @param indexType
     * @param id
     * @return
     */
    public Map<String,Object> findDocument(String indexName,String[] fetchSource,String id){
        Map<String, Object> data=null;
        try{
            GetRequest request = new GetRequest(indexName, id);
            String[] includes =fetchSource;
            String[] excludes = Strings.EMPTY_ARRAY;
            FetchSourceContext fetchSourceContext =
                    new FetchSourceContext(true, includes, excludes);
            request.fetchSourceContext(fetchSourceContext);
            GetResponse response = restHighLevelClient.get(request, RequestOptions.DEFAULT);
            data= response.getSourceAsMap();
        }catch (IOException e){
            log.error("findDocument fail", e);
        }
        return data;
    }




    /**
     * 批量插入数据 只支持JSON 格式
     * @param indexName
     * @param indexType
     * @param datas
     */
    public Boolean batchSave(String indexName,Map<String, String> datas){
        boolean isSuccess =false;
        if (datas == null || datas.isEmpty()) {
            return isSuccess;
        }
        try{
            long now = System.currentTimeMillis();
            BulkRequest request = new BulkRequest();
            for (Map.Entry<String, String> entry : datas.entrySet()) {
            	IndexRequest index=new IndexRequest(indexName).source(entry.getValue(), XContentType.JSON);
            	index.id(entry.getKey());
            	request.add(index);
            }
            BulkResponse bulkResponse = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
            if (bulkResponse.hasFailures()) {
                logger.error("batchSave index name:{}  pageSie:{}  failed:{} complate time:{}",indexName, datas.size(), bulkResponse.buildFailureMessage(), (System.currentTimeMillis() - now) + "");
            }else{
                if (logger.isDebugEnabled()) {
                    logger.debug("batchSave index name:{} pageSie:{} success complate time:{}",indexName, datas.size(), (System.currentTimeMillis() - now) + "");
                }
                isSuccess=true;
            }
        }catch (IOException e){
            log.error("batchSave fail", e);
        }
        datas = null;
        return isSuccess;
    }

    /**
     * 批量删除
     * @param indexName
     * @param indexType
     * @param datas
     * @return
     */
    public Boolean batchDelete(String indexName, String indexType, Set<String> datas){
        boolean isSuccess =false;
        if (datas == null || datas.isEmpty()) {
            return isSuccess;
        }
        try{
            BulkRequest request = new BulkRequest();
            for (String id : datas) {
                request.add(new DeleteRequest(indexName,id));
            }
            BulkResponse bulkResponse = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
            if (bulkResponse.hasFailures()) {
                for (BulkItemResponse item : bulkResponse.getItems()) {
                    logger.error("batchDelete fail:{}",item.getFailureMessage());
                }
            } else {
                isSuccess = true;
            }
        }catch (IOException e){
            log.error("batchDelete fail", e);
        }
        return isSuccess;
    }

    /**
     * 批量修改
     * @param indexName
     * @param indexType
     * @param datas
     * @return
     */
    public Boolean batchUpdate(String indexName,Map<String, String> datas){
        boolean isSuccess =false;
        if (datas == null || datas.isEmpty()) {
            return isSuccess;
        }
        BulkRequest request = new BulkRequest();
        for (Map.Entry<String, String> entry : datas.entrySet()) {
            request.add(new UpdateRequest(indexName,entry.getKey()).doc(XContentType.JSON,entry.getValue()));
        }
        try{
            BulkResponse bulkResponse = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
            if (bulkResponse.hasFailures()) {
                for (BulkItemResponse item : bulkResponse.getItems()) {
                    logger.error("batchUpdate fail:{}",item.getFailureMessage());
                }
            } else {
                isSuccess = true;
            }
        }catch (IOException e){
            log.error("batchUpdate fail", e);
        }
        return isSuccess;
    }

}
