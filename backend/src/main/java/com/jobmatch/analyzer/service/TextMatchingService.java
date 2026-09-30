package com.jobmatch.analyzer.service;

import org.springframework.stereotype.Service;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class TextMatchingService {
    private static final List<String> SKILLS = List.of("java","spring boot","spring","python","javascript","typescript","react","angular","node.js","sql","postgresql","mysql","mongodb","docker","kubernetes","terraform","jenkins","aws","azure","gcp","git","rest api","machine learning","pandas","numpy","tensorflow","css","html","linux","communication","leadership");
    public Result analyze(String resume, String job) {
        String normalized = job.toLowerCase(Locale.ROOT);
        List<String> required = SKILLS.stream().filter(s -> contains(normalized,s)).toList();
        List<String> matching = required.stream().filter(s -> contains(resume.toLowerCase(Locale.ROOT),s)).toList();
        List<String> missing = required.stream().filter(s -> !matching.contains(s)).toList();
        double score = required.isEmpty() ? 0 : Math.round(matching.size() * 1000.0 / required.size()) / 10.0;
        List<String> tips = missing.isEmpty() ? List.of("No dictionary skills are missing. Review the role requirements and tailor your experience examples.") : missing.stream().limit(5).map(s -> "If you have this experience, add a truthful example showing how you used " + s + ".").toList();
        return new Result(score,matching,missing,tips);
    }
    private boolean contains(String text, String skill) {
        String quoted = Pattern.quote(skill);
        return Pattern.compile("(?i)(?<![\\p{L}\\p{N}])" + quoted + "(?![\\p{L}\\p{N}])").matcher(text).find();
    }
    public record Result(double score,List<String> matching,List<String> missing,List<String> suggestions) {}
}
