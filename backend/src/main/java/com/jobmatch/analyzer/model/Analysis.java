package com.jobmatch.analyzer.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class Analysis {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, length = 120) private String title;
    @Column(nullable = false, columnDefinition = "text") private String resumeText;
    @Column(nullable = false, columnDefinition = "text") private String jobDescriptionText;
    @Column(nullable = false) private double matchPercentage;
    @Column(nullable = false, columnDefinition = "text") private String matchingKeywords;
    @Column(nullable = false, columnDefinition = "text") private String missingKeywords;
    @Column(nullable = false, columnDefinition = "text") private String suggestions;
    @Column(nullable = false) private Instant createdAt = Instant.now();
    protected Analysis() {}
    public Analysis(String title, String resumeText, String jobDescriptionText, double score, String matches, String missing, String suggestions) {
        this.title=title; this.resumeText=resumeText; this.jobDescriptionText=jobDescriptionText; this.matchPercentage=score; this.matchingKeywords=matches; this.missingKeywords=missing; this.suggestions=suggestions;
    }
    public Long getId(){return id;} public String getTitle(){return title;} public String getResumeText(){return resumeText;} public String getJobDescriptionText(){return jobDescriptionText;} public double getMatchPercentage(){return matchPercentage;} public String getMatchingKeywords(){return matchingKeywords;} public String getMissingKeywords(){return missingKeywords;} public String getSuggestions(){return suggestions;} public Instant getCreatedAt(){return createdAt;}
}
