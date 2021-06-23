package com.gmail.petrikov05.app.web.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@Profile("!test")
public class APIExceptionHandler {

    private static final String ARGUMENT_TYPE_INTERNAL_SERVER_ERROR = "Inner exception";
    private static final String ARGUMENT_TYPE_MISMATCH_MESSAGE = "Bad request";

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    ResponseError handleValidationError(MethodArgumentNotValidException e) {
        ResponseError responseError = new ResponseError();
        StringBuilder stringBuilder = new StringBuilder();
        for (ObjectError error : e.getBindingResult().getAllErrors()) {
            String defaultMessage = error.getDefaultMessage();
            stringBuilder.append(defaultMessage);
        }
        responseError.setMessage(stringBuilder.toString());
        return responseError;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    ResponseError handleParameterParseError(MethodArgumentTypeMismatchException e) {
        ResponseError responseError = new ResponseError();
        responseError.setMessage(ARGUMENT_TYPE_MISMATCH_MESSAGE);
        return responseError;
    }

    public static class ResponseError {

        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }

}
