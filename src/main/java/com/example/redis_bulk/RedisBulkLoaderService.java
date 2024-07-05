package com.example.redis_bulk;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
@Slf4j
public class RedisBulkLoaderService {

    public void bulkLoadService(String filePath) throws IOException, InterruptedException {

        log.info("Bulk load in redis process started");
//        For docker redis
        ProcessBuilder processBuilder = new ProcessBuilder(
                "docker", "exec", "-i", "docker-compose-redis-1", "redis-cli", "--pipe"
        );
//        For redis installed in host
//        ProcessBuilder processBuilder = new ProcessBuilder(
//                "redis-cli", "--pipe"
//        );
        processBuilder.redirectInput(new java.io.File(filePath));
        Process process = processBuilder.start();
        String s;
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

        while ((s = stdInput.readLine()) != null) {
            log.info("STDOUT: " + s);
        }

        while ((s = stdError.readLine()) != null) {
            log.error("STDERR: " + s);
        }
        int exitCode = process.waitFor();
        System.out.println(exitCode);
        log.info("Bulk load in redis finished");
    }

}
