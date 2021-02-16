package com.reizu.snaphs.api.exception

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.persistence.EntityNotFoundException
import javax.persistence.RollbackException

/**
 * Abstract class for Exception Handling
 */
abstract class BaseResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [ EntityNotFoundException::class ])
    fun handleNotFound(exception: RuntimeException, request: WebRequest): ResponseEntity<Any> {
        val bodyOfResponse = "Entity not found"
        return handleExceptionInternal(exception, bodyOfResponse, HttpHeaders(), HttpStatus.NOT_FOUND, request)
    }

    @ExceptionHandler(value = [ RollbackException::class ])
    fun handleBadRequest(exception: RuntimeException, request: WebRequest): ResponseEntity<Any> {
        val bodyOfResponse = "Action would put entity into invalid state"
        return handleExceptionInternal(exception, bodyOfResponse, HttpHeaders(), HttpStatus.BAD_REQUEST, request)
    }

    @ExceptionHandler(value = [ ])
    fun handleForbidden(exception: RuntimeException, request: WebRequest): ResponseEntity<Any> {
        val bodyOfResponse = "Action not allowed on entity"
        return handleExceptionInternal(exception, bodyOfResponse, HttpHeaders(), HttpStatus.FORBIDDEN, request)
    }

    @ExceptionHandler(value = [ IllegalArgumentException::class, IllegalStateException::class ])
    fun handleConflict(exception: RuntimeException, request: WebRequest): ResponseEntity<Any> {
        val bodyOfResponse = "Invalid request"
        return handleExceptionInternal(exception, bodyOfResponse, HttpHeaders(), HttpStatus.CONFLICT, request)
    }

}
