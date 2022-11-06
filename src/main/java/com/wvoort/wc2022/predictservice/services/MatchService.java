package com.wvoort.wc2022.predictservice.services;

import com.wvoort.wc2022.predictservice.model.Matches;
import com.wvoort.wc2022.predictservice.model.Prediction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MatchService {

    private final static String MATCHES_CACHE_KEY ="matches";

    @Value("${fixture.api}")
    private String fixtureApi;

    @Autowired
    private RestTemplate restTemplate;





    private String getRawMatches() {

        final ResponseEntity<String> responseEntity =
                restTemplate.getForEntity(fixtureApi , String.class);
        return responseEntity.getBody();
    }



    @Cacheable(value = MATCHES_CACHE_KEY)
    public Matches getMatches() {

        return Matches.fromJsonResponseString(getRawMatches());
    }
}
