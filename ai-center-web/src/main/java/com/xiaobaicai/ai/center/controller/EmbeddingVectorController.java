package com.xiaobaicai.ai.center.controller;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.*;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author xiaobaicai
 */
@Slf4j
@RestController
@RequestMapping("embedding-vector")
public class EmbeddingVectorController {

    @Resource
    private EmbeddingModel embeddingModel;

    @Resource
    private VectorStore vectorStore;

    @GetMapping("text2embedding")
    public EmbeddingResponse text2Embedding(@RequestParam("msg") String msg) {
        String model = "text-embedding-v3";
        EmbeddingResponse response = embeddingModel.call(new EmbeddingRequest(List.of(msg), EmbeddingOptionsBuilder.builder().withModel(model).build()));
        log.info(Arrays.toString(response.getResult().getOutput()));
        return response;
    }

    @GetMapping("embedding2vector/add")
    public void add() {
        List<Document> docs = List.of(
                new Document("I study LLM"),
                new Document("I love Java")
        );
        vectorStore.add(docs);
    }

    @GetMapping("embedding2vector/get")
    public List<Document> list(@RequestParam("msg") String msg) {
        return vectorStore.similaritySearch(msg);
    }

}
