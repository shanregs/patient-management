package com.shan.ms.pm.patientservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "billing.service.grpc")

public class GrpcBillingProperties {
    private String serverAddress;
    private int port;
}
