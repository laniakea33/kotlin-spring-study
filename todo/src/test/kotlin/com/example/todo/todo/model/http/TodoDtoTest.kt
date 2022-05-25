package com.example.todo.todo.model.http

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.validation.FieldError
import java.time.LocalDateTime
import javax.validation.Validation


class TodoDtoTest {

    //  DTO의 유효성 검사기. 각 필드가 유효한지 알 수 있다.
    val validator = Validation.buildDefaultValidatorFactory().validator

    @Test
    fun todoDtoTest() {
        val todoDto = TodoDto().apply {
            title = "테스트"
            description = "테스트 디스크립션"
            schedule = "1111-22-33 44:55:66"
        }

        val result = validator.validate(todoDto)

        result.forEach {
            println(it.propertyPath.last().name)
            println(it.message)
            println(it.invalidValue)
        }

        Assertions.assertEquals(true, result.isEmpty())

    }
}