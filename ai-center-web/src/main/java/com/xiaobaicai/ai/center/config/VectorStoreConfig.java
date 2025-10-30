package com.xiaobaicai.ai.center.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xiaobaicai
 */
@Slf4j
@Configuration
public class VectorStoreConfig {

    @Value("classpath:rag/vector-store.txt")
    private Resource vectorStoreFile;

    @jakarta.annotation.Resource
    private StringRedisTemplate stringRedisTemplate;

    @jakarta.annotation.Resource
    private VectorStore vectorStore;

    @PostConstruct
    public void init() {
        try {
            TextReader textReader = new TextReader(vectorStoreFile);
            String filename = vectorStoreFile.getFilename();
            String key = "vector-store:%s".formatted(filename);
            List<Document> documentList = new TokenTextSplitter().split(textReader.get());
            String docIds = Optional.ofNullable(documentList).orElse(Collections.emptyList()).stream().map(Document::getId).collect(Collectors.joining(","));

            VectorInfoModel vectorInfo = new VectorInfoModel();
            vectorInfo.setIds(docIds);
            vectorInfo.setLastModified(vectorStoreFile.lastModified());
            Object dbObject = stringRedisTemplate.opsForValue().get(key);
            if (dbObject == null) {
                stringRedisTemplate.opsForValue().setIfAbsent(key, JSON.toJSONString(vectorInfo));
                if (documentList != null) {
                    vectorStore.add(documentList);
                    log.info("Vector store loaded successfully.");
                }
                return;
            }
            VectorInfoModel dbRecord = JSONObject.parseObject(dbObject.toString(), VectorInfoModel.class);
            if (dbRecord.getLastModified().equals(vectorInfo.getLastModified())) {
                log.info("Vector store nothing to update.");
                return;
            }
            String dbIds = dbRecord.getIds();
            if (documentList != null) {
                stringRedisTemplate.opsForValue().setIfAbsent(key, JSON.toJSONString(vectorInfo));
                vectorStore.delete(Arrays.asList(dbIds.split(",")));
                vectorStore.add(documentList);
                log.info("Vector store loaded successfully.");
            }

        } catch (Throwable ex) {
            log.error("vector store load error", ex);
        }
    }


    @Data
    public static class VectorInfoModel implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;

        private Long lastModified;

        private String ids;
    }
}
