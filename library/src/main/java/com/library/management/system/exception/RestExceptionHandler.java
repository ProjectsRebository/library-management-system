package com.library.management.system.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({NotFoundException.class})
    @ResponseBody
    public ResponseEntity<Map<String, Object>> handleNotFoundException(NotFoundException exception, WebRequest request) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("status", HttpStatus.NOT_FOUND.value());
        errorDetails.put("error", HttpStatus.NOT_FOUND.getReasonPhrase());
        errorDetails.put("code", "ENTITY_NOT_FOUND");
        errorDetails.put("message", exception.getMessage());
        errorDetails.put("path", request.getDescription(false).replace("uri=", ""));

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({InvalidDataException.class})
    @ResponseBody
    public ResponseEntity<Map<String, Object>> handleInvalidDataException(InvalidDataException exception, WebRequest request) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("status", HttpStatus.BAD_REQUEST.value());
        errorDetails.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        errorDetails.put("code", "INVALID_DATA");
        errorDetails.put("message", exception.getMessage());
        errorDetails.put("path", request.getDescription(false).replace("uri=", ""));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
