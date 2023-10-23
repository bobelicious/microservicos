package com.augusto.address.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {
    @Value("${app.output-kafka-topic-product}")
    private String outputKafkaTopicProduct;
}
