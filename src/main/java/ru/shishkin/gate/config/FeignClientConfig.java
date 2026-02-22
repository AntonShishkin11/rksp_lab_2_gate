package ru.shishkin.gate.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.shishkin.gate.client.ApiClient;
import ru.shishkin.gate.client.api.StudentsApi;

@Configuration
public class FeignClientConfig {

    @Bean
    public StudentsApi studentsFeignClient(@Value("${data.service.url}") String baseUrl) {
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(baseUrl);
        return apiClient.buildClient(StudentsApi.class);
    }
}