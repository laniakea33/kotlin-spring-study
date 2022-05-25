package com.example.mvc.controller.exception

import com.example.mvc.model.http.Error
import com.example.mvc.model.http.ErrorResponse
import com.example.mvc.model.http.UserRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest
import javax.validation.ConstraintViolationException
import javax.validation.Valid
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@RestController
@Validated
@RequestMapping("/api/exception")
class ExceptionApiController {

    @GetMapping("/hello")
    //  예외가 발생하면 서버가 디폴트로 500에러를 내린다.
    fun hello(): String {
        val array = arrayOf<String>()
//        array[2]
        return "hello"
    }

    @GetMapping("")
    fun get(
        @NotBlank
        @Size(min = 2, max = 6)
        @RequestParam name: String,

        @Min(10)
        @RequestParam age: Int,
    ): String {
        println(name)
        println(age)
        return "$name $age"
    }

    //  ConstraintViolationException : 유효성 검증을 통과 못했을 때 발생함.
    @ExceptionHandler(value = [ConstraintViolationException::class])
    fun constraintViolationException(
        request: HttpServletRequest,    //  이 파라미터를 추가하면 함수 탈 때 스프링이 인자를 넣어준다.
        e: ConstraintViolationException,
    ): ResponseEntity<ErrorResponse> {
        val errors = mutableListOf<Error>()
        e.constraintViolations.forEach {
            val field = it.propertyPath.last().name //  검증 실패한 프로퍼티의 이름이 last()에 있음.
            val message = it.message
            val value = it.invalidValue
            errors.add(Error(field, message, value))
        }

        val errorResponse = ErrorResponse().apply {
            this.resultCode = "fail"
            this.httpStatus = HttpStatus.BAD_REQUEST.value().toString()
            this.httpMethod = request.method
            this.message = "요청에 에러가 발생하였습니다."
            this.path = request.requestURI.toString()
            this.timestamp = LocalDateTime.now()
            this.errors = errors
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun methodArgumentNotValidException(
        request: HttpServletRequest,
        e: MethodArgumentNotValidException,
    ): ResponseEntity<ErrorResponse> {
        val errors = mutableListOf<Error>()
        e.bindingResult.allErrors.forEach { errorObject ->
            val field = (errorObject as FieldError).field
            val message = errorObject.defaultMessage
            val value = errorObject.rejectedValue
            errors.add(Error(field, message, value))
        }

        val errorResponse = ErrorResponse().apply {
            this.resultCode = "fail"
            this.httpStatus = HttpStatus.BAD_REQUEST.value().toString()
            this.httpMethod = request.method
            this.message = "요청에 에러가 발생하였습니다."
            this.path = request.requestURI.toString()
            this.timestamp = LocalDateTime.now()
            this.errors = errors
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    @PostMapping("")
    fun post(@Valid @RequestBody userRequest: UserRequest): UserRequest {
        println(userRequest)
        return userRequest
    }

    //  Controller Advice가 있더라도, Controller클래스 내부에 @ExceptionHandler 함수가 있으면 예외 발생시 이 함수를 통하게 된다.
    @ExceptionHandler(value = [IndexOutOfBoundsException::class])
    fun indexOutOfBoundsException(e : IndexOutOfBoundsException): ResponseEntity<String> {
        println("controller exception handler")
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Index Error")
    }
}