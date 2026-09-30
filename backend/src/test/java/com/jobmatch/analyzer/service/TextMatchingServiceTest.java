package com.jobmatch.analyzer.service;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class TextMatchingServiceTest {
    private final TextMatchingService service=new TextMatchingService();
    @Test void computesProportionAndAvoidsSubstringFalsePositive(){
        var result=service.analyze("React Git JavaScript","Frontend role requiring React, TypeScript, CSS and Git. Java experience required.");
        assertEquals(40.0,result.score());
        assertTrue(result.matching().contains("react"));
        assertTrue(result.missing().contains("java"));
    }
    @Test void givesZeroWhenNoKnownSkillsAppear(){
        var result=service.analyze("Marketing coordinator","Seeking an adaptable professional with clear written communication skills.");
        assertEquals(0.0,result.score());
        assertTrue(result.matching().isEmpty());
    }
}
