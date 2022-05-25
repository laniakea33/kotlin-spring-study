package com.example.todo.todo.handler

import com.example.todo.todo.controller.api.todo.TodoApiController
import com.example.todo.todo.model.http.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest

@ControllerAdvice(basePackageClasses = [TodoApiController::class])
class TodoApiControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValidException(e: MethodArgumentNotValidException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        val errors = mutableListOf<com.example.todo.todo.model.http.Error>()

        e.bindingResult.allErrors.forEach { errorObject ->
            com.example.todo.todo.model.http.Error().apply {
                field = (errorObject as FieldError).field
                message = errorObject.defaultMessage
                value = errorObject.rejectedValue
            }.also {
                errors.add(it)
            }
        }

        val errorResponse = ErrorResponse().apply {
            resultCode = "fail"
            httpStatus = HttpStatus.BAD_REQUEST.value().toString()
            httpMethod = request.method
            message = ""
            path = request.requestURI
            timestamp = LocalDateTime.now()
            this.errors = errors
        }

        return ResponseEntity.badRequest().body(errorResponse)
    }
}