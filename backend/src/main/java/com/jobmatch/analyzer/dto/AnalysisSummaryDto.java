package com.jobmatch.analyzer.dto;
import java.time.Instant;
public record AnalysisSummaryDto(Long id,String title,double matchPercentage,Instant createdAt) {}
