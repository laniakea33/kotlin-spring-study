package com.example.mvc.advice

import com.example.mvc.controller.exception.ExceptionApiController
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

//  글로벌하게 예외를 처리하는 방법
//  Rest Controller를 통하는 모든 예외들이 여기를 통과한다.
//  basePackageClasses 속성을 통해 특정 클래스 지정도 가능
//  @ControllerAdvice
//@RestControllerAdvice(basePackageClasses = [ExceptionApiController::class])
class GlobalControllerAdvice {

    @ExceptionHandler(value = [RuntimeException::class])    //  해당 함수가 받을 Exception의 타입임
    fun exception(e : RuntimeException): String {   //  200 OK
        //  여기서 catch를 했기 때문에 에러는 발생하지 않는다.
        return "Server Error"
    }

    @ExceptionHandler(value = [IndexOutOfBoundsException::class])
    fun indexOutOfBoundsException(e : IndexOutOfBoundsException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Index Error")
    }
}