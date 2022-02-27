package com.challenge.resource.api.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.logging.LogLevel;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.io.IOException;
import java.sql.SQLException;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ControllerExceptionHandler {

    private final HttpStatus DEFAULT_HTTP_STATUS = HttpStatus.BAD_REQUEST;

    @ExceptionHandler
    public ResponseEntity<?> handleException(Exception ex) {

        String message = ex.getMessage();

        return handleExceptionLogic(message, null);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(SQLException ex) {

        String message = "SQL exception message";
        message += ": " + ex.getMessage();

        return handleExceptionLogic(message, null);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(DuplicateKeyException ex) {

        String message = ex.getMessage();
        return handleExceptionLogic(message, null);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(DataIntegrityViolationException ex) {

        String message = "Data integrity violation exception message";
        message += ": " + ex.getMessage();

        return handleExceptionLogic(message, null);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(IllegalArgumentException ex) {

        String message = ex.getMessage();
        return handleExceptionLogic(message, null);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(IOException ex) {

        String message = "IO exception message";
        message += ": " + ex.getMessage();

        return handleExceptionLogic(message, null);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(NullPointerException ex) {

        String message = "Null pointer exception message";
        message += ": " + ex.getMessage();

        try {
            if (ex.getStackTrace().length > 0) {
                log.error("Null exception file: [" + ex.getStackTrace()[0].getFileName() + "]" +
                        " method: [" + ex.getStackTrace()[0].getMethodName() + "]" +
                        " line: [" + ex.getStackTrace()[0].getLineNumber() + "]" +
                        " class: [" + ex.getStackTrace()[0].getClassName() + "]");
            }
        } catch (Exception e) {
            // ignore
        }

        return handleExceptionLogic(message, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<?> handleExceptionLogic(String message, HttpStatus responseStatus) {

        if (responseStatus == null) {
            responseStatus = DEFAULT_HTTP_STATUS;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(new WebApiResponse<>(responseStatus, message), headers, responseStatus);
    }

    private void logException(LogLevel lvl, Exception ex) {
        String msg = "" + ex;
        if (lvl.equals(LogLevel.TRACE)) {
            log.trace(msg);
        } else if (lvl.equals(LogLevel.DEBUG)) {
            log.debug(msg);
        } else if (lvl.equals(LogLevel.INFO)) {
            log.info(msg);
        } else if (lvl.equals(LogLevel.WARN)) {
            log.warn(msg);
        } else if (lvl.equals(LogLevel.ERROR)) {
            log.error(msg);
        } else if (lvl.equals(LogLevel.FATAL)) {
            log.error(msg);
        }
    }
}