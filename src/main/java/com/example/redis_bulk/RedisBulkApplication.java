package com.example.redis_bulk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@SpringBootApplication
public class RedisBulkApplication implements CommandLineRunner {

    @Autowired
    private RedisCommandFileService redisCommandFileService;

    public static void main(String[] args) {
        SpringApplication.run(RedisBulkApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String filePath = "abc.txt";
        Random random = new Random();
        Map<String, Student> data = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            String id = String.valueOf(random.nextInt());
            String name = generateRandomString(5);
            data.put(id + "^" + name, new Student(id, name, random.nextInt()));
        }
        redisCommandFileService.generateFile(data, filePath);
    }

    public static String generateRandomString(int length) {
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            stringBuilder.append(CHARACTERS.charAt(randomIndex));
        }

        return stringBuilder.toString();
    }

}
