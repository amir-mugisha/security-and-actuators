package org.example.product_management_system.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> response(String message, HttpStatus status, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("HttpStatus", status);
        response.put("message", message);
        response.put("data", data);

        return new ResponseEntity<>(response, status);
    }
}
