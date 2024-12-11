package com.example.tdesdemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BlockChipHerConfig {

    @Value("${spring.tdes.secret.key}")
    private String secretKey;

    @Value("${spring.tdes.vector.key}")
    private String vectorKey;

    @Bean
    public BlockChipHerUtil blockChipHerUtil() {
        return new BlockChipHerUtil(secretKey, vectorKey);
    }
}
