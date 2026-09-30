package com.jobmatch.analyzer.dto;
import java.time.Instant;
import java.util.List;
public record AnalyzeResponse(Long id,String title,double matchPercentage,List<String> matchingKeywords,List<String> missingKeywords,List<String> suggestions,Instant createdAt) {}
