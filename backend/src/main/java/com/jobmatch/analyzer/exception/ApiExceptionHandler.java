package com.jobmatch.analyzer.exception;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(AnalysisNotFoundException.class) ResponseEntity<Map<String,Object>> notFound(RuntimeException ex){return ResponseEntity.status(404).body(Map.of("status",404,"error",ex.getMessage()));}
    @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<Map<String,Object>> invalid(MethodArgumentNotValidException ex){Map<String,String> fields=new LinkedHashMap<>();ex.getBindingResult().getFieldErrors().forEach(e->fields.put(e.getField(),e.getDefaultMessage()));return ResponseEntity.badRequest().body(Map.of("status",400,"error","Validation failed","fieldErrors",fields));}
}
