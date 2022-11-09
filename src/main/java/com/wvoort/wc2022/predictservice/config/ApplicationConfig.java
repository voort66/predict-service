package com.wvoort.wc2022.predictservice.config;

import com.wvoort.wc2022.predictservice.model.Policy;
import com.wvoort.wc2022.predictservice.model.PredictionPolicyFactory;
import com.wvoort.wc2022.predictservice.model.PredictionPolicyFactoryImpl;
import com.wvoort.wc2022.predictservice.services.MatchService;
import com.wvoort.wc2022.predictservice.services.PredictionService;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

@Configuration
@EntityScan("com.wvoort.wc2022.predictservice.model")
public class ApplicationConfig {

    @Bean
    public RestTemplate restTemplate() {

        CloseableHttpClient client = HttpClients.custom().setSSLHostnameVerifier(new HostnameVerifier() {

            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        }).build();
        HttpComponentsClientHttpRequestFactory req = new HttpComponentsClientHttpRequestFactory(client);


        return new RestTemplate(req);
    }

    @Bean
    public PredictionPolicyFactory predictionPolicyFactory() {
        return new PredictionPolicyFactoryImpl();
    }







}
