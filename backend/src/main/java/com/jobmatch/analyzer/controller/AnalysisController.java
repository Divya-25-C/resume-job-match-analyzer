package com.jobmatch.analyzer.controller;
import com.jobmatch.analyzer.dto.*;
import com.jobmatch.analyzer.service.AnalysisService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api")
public class AnalysisController {
    private final AnalysisService service;
    public AnalysisController(AnalysisService service){this.service=service;}
    @PostMapping("/analyze") @ResponseStatus(HttpStatus.CREATED) public AnalyzeResponse analyze(@Valid @RequestBody AnalyzeRequest request){return service.analyze(request);}
    @GetMapping("/analyses") public List<AnalysisSummaryDto> history(){return service.history();}
    @GetMapping("/analyses/{id}") public AnalyzeResponse get(@PathVariable Long id){return service.get(id);}
    @DeleteMapping("/analyses/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id){service.delete(id);}
    @GetMapping("/health") public String health(){return "OK";}
}
