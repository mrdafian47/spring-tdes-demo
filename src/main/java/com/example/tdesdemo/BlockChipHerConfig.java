package com.example.tdesdemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BlockChipHerConfig {

    @Value("${spring.crypto.desede.key.part.one}")
    private String keyPartOne;

    @Value("${spring.crypto.desede.key.part.one}")
    private String keyPartTwo;

    @Bean
    public BlockChipHerUtil blockChipHerUtil() {
        return new BlockChipHerUtil(keyPartOne, keyPartTwo);
    }
}
