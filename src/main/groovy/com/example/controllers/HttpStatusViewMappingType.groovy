package com.example.controllers

import org.springframework.http.HttpStatus

enum HttpStatusViewMappingType {

    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), '/error/400'),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), '/error/403'),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), '/error/500'),
    SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE.value(), '/error/503'),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED.value(), '/error/403'),
    NOT_FOUND(HttpStatus.NOT_FOUND.value(), '/error/404')

    Integer httpStatusCode
    String view

    def HttpStatusViewMappingType(Integer httpStatusCode, String view) {
        this.httpStatusCode = httpStatusCode
        this.view = view
    }

    static HttpStatusViewMappingType getByHttpStatusCode(Integer httpStatusCode){
        values()?.find { it.httpStatusCode == httpStatusCode}
    }

}
