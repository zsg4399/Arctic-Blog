package com.hnust.zsg.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.index.Settings;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.ByQueryResponse;
import org.springframework.data.elasticsearch.core.query.MoreLikeThisQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;


/**
 * elasticsearch的工具类，封装一些API方便直接调用
 *
 * @author 86187
 */
@Component
public final class ElasticSearchUtil {

    @Autowired
    private ElasticsearchRestTemplate template;


    public IndexOperations indexOps(String... indexName) {
        return template.indexOps(IndexCoordinates.of(indexName));
    }


    public IndexOperations indexOps(Class<?> clazz) {
        return template.indexOps(clazz);
    }

    public Settings getSettings(String indexName){
        return indexOps(indexName).getSettings();
    }

    public Settings getSettings(Class<?> clazz){
        return indexOps(clazz).getSettings();
    }
    /**
     * 生成映射规则
     *
     * @param clazz
     * @return Document
     */
    public Document createMapping(Class<?> clazz) {
        return indexOps(clazz).createMapping();
    }

    /**
     * 向指定名称的索引中推送映射
     *
     * @param indexName
     * @param clazz
     * @return
     */
    public boolean putMapping(String indexName, Class<?> clazz) {
        return putMapping(indexName, createMapping(clazz));
    }

    public boolean putMapping(String indexName, Document document) {
        return indexOps(indexName).putMapping(document);
    }

    public <T> T save(T entity) {
        return template.save(entity);
    }

    public <T> T save(T entity, IndexCoordinates index) {
        return template.save(entity, index);
    }

    public <T> Iterable<T> save(Iterable<T> entities) {
        return template.save(entities);
    }

    public <T> Iterable<T> save(Iterable<T> entities, IndexCoordinates index) {
        return template.save(entities, index);
    }

    public <T> Iterable<T> save(T... entities) {
        return save((Iterable) Arrays.asList(entities));
    }

    public String delete(String id, Class<?> entityType) {
        return template.delete(id, entityType);
    }

    public ByQueryResponse delete(Query query, Class<?> clazz) {
        return template.delete(query, clazz);
    }

    public String delete(Object entity) {
        return template.delete(entity);
    }

    public String delete(Object entity, IndexCoordinates index) {
        return template.delete(entity, index);
    }

    public String delete(String id, IndexCoordinates index) {
        return template.delete(id, index);
    }

    public <T> SearchHits<T> search(Query query, Class<T> clazz) {
        return template.search(query, clazz);
    }

    public <T> SearchHits<T> search(Query query, Class<T> clazz, IndexCoordinates index) {
        return template.search(query, clazz, index);
    }

    public <T> SearchHits<T> search(MoreLikeThisQuery query, Class<T> clazz) {
        return template.search(query, clazz);
    }

    public <T> SearchHits<T> search(MoreLikeThisQuery query, Class<T> clazz, IndexCoordinates index) {
        return template.search(query, clazz, index);
    }

    public <T> SearchHit<T> searchOne(Query query, Class<T> clazz) {
        return template.searchOne(query, clazz);
    }

    public <T> SearchHit<T> searchOne(Query query, Class<T> clazz, IndexCoordinates index) {
        return template.searchOne(query, clazz, index);
    }

    public <T> List<SearchHits<T>> multiSearch(List<? extends Query> queries, Class<T> clazz) {
        return template.multiSearch(queries, clazz);
    }

    <T> List<SearchHits<T>> multiSearch(List<? extends Query> queries, Class<T> clazz, IndexCoordinates index) {
        return template.multiSearch(queries, clazz, index);
    }

    List<SearchHits<?>> multiSearch(List<? extends Query> queries, List<Class<?>> classes) {
        return template.multiSearch(queries, classes);
    }

    List<SearchHits<?>> multiSearch(List<? extends Query> queries, List<Class<?>> classes, IndexCoordinates index) {
        return template.multiSearch(queries, classes, index);
    }


}
