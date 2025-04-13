package com.shan.ms.pm.analyticsservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "kafka.consumer")
public class KafkaConsumerProperties {
    private String topic;
    private String groupId;
}
