package com.jobmatch.analyzer.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobmatch.analyzer.dto.AnalyzeRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest @AutoConfigureMockMvc @ActiveProfiles("test")
class AnalysisControllerTest {
    @Autowired MockMvc mvc; @Autowired ObjectMapper mapper;
    @Test void validatesAndPersistsAnalysisThenDeletesIt() throws Exception {
        var req=new AnalyzeRequest("Demo","Java Spring Boot PostgreSQL Docker","Backend role needs Java, Spring Boot and Docker experience.");
        String response=mvc.perform(post("/api/analyze").contentType("application/json").content(mapper.writeValueAsString(req))).andExpect(status().isCreated()).andExpect(jsonPath("$.matchPercentage").exists()).andReturn().getResponse().getContentAsString();
        long id=mapper.readTree(response).get("id").asLong();
        mvc.perform(get("/api/analyses/"+id)).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(id));
        mvc.perform(delete("/api/analyses/"+id)).andExpect(status().isNoContent());
        mvc.perform(get("/api/analyses/"+id)).andExpect(status().isNotFound());
    }
    @Test void rejectsBlankResume() throws Exception {
        var req=new AnalyzeRequest(null," ","This job description is longer than twenty characters.");
        mvc.perform(post("/api/analyze").contentType("application/json").content(mapper.writeValueAsString(req))).andExpect(status().isBadRequest()).andExpect(jsonPath("$.fieldErrors.resumeText").exists());
    }
}
