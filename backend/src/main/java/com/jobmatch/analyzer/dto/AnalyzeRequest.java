package com.jobmatch.analyzer.dto;
import jakarta.validation.constraints.*;
public record AnalyzeRequest(@Size(max=120) String title, @NotBlank @Size(max=30000) String resumeText, @NotBlank @Size(min=20,max=30000) String jobDescriptionText) {}
