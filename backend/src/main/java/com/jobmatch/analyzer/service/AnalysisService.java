package com.jobmatch.analyzer.service;

import com.jobmatch.analyzer.dto.*;
import com.jobmatch.analyzer.exception.AnalysisNotFoundException;
import com.jobmatch.analyzer.model.Analysis;
import com.jobmatch.analyzer.repository.AnalysisRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class AnalysisService {
    private final AnalysisRepository repository; private final TextMatchingService matcher;
    public AnalysisService(AnalysisRepository repository,TextMatchingService matcher){this.repository=repository;this.matcher=matcher;}
    @Transactional public AnalyzeResponse analyze(AnalyzeRequest request){
        var r=matcher.analyze(request.resumeText(),request.jobDescriptionText());
        var a=repository.save(new Analysis(request.title()==null||request.title().isBlank()?"Untitled analysis":request.title().trim(),request.resumeText(),request.jobDescriptionText(),r.score(),String.join("\n",r.matching()),String.join("\n",r.missing()),String.join("\n",r.suggestions())));
        return response(a);
    }
    @Transactional(readOnly=true) public List<AnalysisSummaryDto> history(){return repository.findAllByOrderByCreatedAtDesc().stream().map(a->new AnalysisSummaryDto(a.getId(),a.getTitle(),a.getMatchPercentage(),a.getCreatedAt())).toList();}
    @Transactional(readOnly=true) public AnalyzeResponse get(Long id){return response(repository.findById(id).orElseThrow(()->new AnalysisNotFoundException(id)));}
    @Transactional public void delete(Long id){if(!repository.existsById(id))throw new AnalysisNotFoundException(id);repository.deleteById(id);}
    private AnalyzeResponse response(Analysis a){return new AnalyzeResponse(a.getId(),a.getTitle(),a.getMatchPercentage(),split(a.getMatchingKeywords()),split(a.getMissingKeywords()),split(a.getSuggestions()),a.getCreatedAt());}
    private List<String> split(String s){return s==null||s.isBlank()?List.of():Arrays.asList(s.split("\\n"));}
}
