package com.wvoort.wc2022.predictservice.controller;

import com.wvoort.wc2022.predictservice.model.Match;
import com.wvoort.wc2022.predictservice.model.Prediction;
import com.wvoort.wc2022.predictservice.model.PredictionPolicyFactory;
import com.wvoort.wc2022.predictservice.services.PredictionService;
import com.wvoort.wc2022.predictservice.services.PredictionValidationService;
import org.aspectj.lang.annotation.Before;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.security.Principal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest({PredictionController.class, PredictionValidationService.class})
class PredictionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PredictionService predictionService;

    @MockBean
    private PredictionPolicyFactory predictionPolicyFactory;

    @Autowired
    private PredictionValidationService predictionValidationService;

    private Principal principal;

    @BeforeEach
    void setUp() {
        Prediction p  = new Prediction();
        p.setAwayGoals(2);
        p.setHomeGoals(4);
        p.setUserName("Wim");

        Match m = new Match();
        m.setAwayTeamName("Senegal");
        m.setHomeTeamLogo("http://google.com");
        m.setHomeTeamName("Ireland");
        m.setAwayTeamLogo("http://google.com");
        m.setStartDate("2022-10-17");
        m.setStartTime("13:15");

        p.setMatchDetails(m);

        when(predictionService.getPredictions(any())).thenReturn(Collections.singletonList(p));


    }


    @Test
    @WithMockUser
    void testGetPredictions() throws Exception {
                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/predictions/create"))
                .andExpect(status().isOk())
                .andReturn();
          String resultSS = result.getResponse().getContentAsString();
          System.out.println(resultSS);
          assertNotNull(resultSS);
//        assertEquals("[{'type': 'season'}]", resultSS);
    }

//    @Test
//    @WithMockUser
//    void testSavePredictions() {
//
//       mockMvc.perform(post("/predictions/save")
//                            .param("name", "duke")
//                            .param("number", "C0124")
//                            .param("email", "duke@java.io"))
//                            .andExpect(status().is3xxRedirection());
//
//        }
//    }
}