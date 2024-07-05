package com.example.redis_bulk;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
public class RedisCommandFileService {

    private final ObjectMapper objectMapper;
    private final RedisBulkLoaderService redisBulkLoaderService;


    public RedisCommandFileService(ObjectMapper objectMapper, RedisBulkLoaderService redisBulkLoaderService) {
        this.objectMapper = objectMapper;
        this.redisBulkLoaderService = redisBulkLoaderService;
    }

    public void generateFile(Map<String, Student> studentMap, String filePath) throws IOException, InterruptedException {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, Student> entry : studentMap.entrySet()) {
                String json = objectMapper.writeValueAsString(entry.getValue());
                writer.write("SET " + entry.getKey() + " '" + json + "'");
                writer.newLine();
            }
        }
        log.info("Data file load completed");
        redisBulkLoaderService.bulkLoadService(filePath);
    }


}
