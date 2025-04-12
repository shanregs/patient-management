package com.shan.ms.pm.patientservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@ConfigurationProperties
@EnableConfigurationProperties(GrpcPropertiesConfig.class)
public class GrpcPropertiesConfig {
}
