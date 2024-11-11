package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/response")
public class StatusController {

    @GetMapping("/{code}")
    public ResponseEntity<Object> getStatus(@PathVariable int code, @RequestParam(required = false) Map<String, String> params) {
        HttpStatus status = getHttpStatus(code);

        if (status != null) {
            return new ResponseEntity<>(createResponseMessage(code, params), status);
        } else {
            return new ResponseEntity<>("Invalid status code, such a response code is not exists!", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{code}")
    public ResponseEntity<Object> postStatus(@PathVariable int code, @RequestParam(required = false) Map<String, String> params) {
        HttpStatus status = getHttpStatus(code);

        if (status != null) {
            return new ResponseEntity<>(createResponseMessage(code, params), status);
        } else {
            return new ResponseEntity<>("Invalid status code, such a response code is not exists!", HttpStatus.BAD_REQUEST);
        }
    }

    private HttpStatus getHttpStatus(int code) {
        try {
            return HttpStatus.valueOf(code);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private Map<String, Object> createResponseMessage(int code, Map<String, String> params) {
        return Map.of(
                "status", code,
                "message", "Everything is fine, this is a simulated response",
                "parameters", params
        );
    }
}
