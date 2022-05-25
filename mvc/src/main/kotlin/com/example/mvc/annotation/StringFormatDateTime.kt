package com.example.mvc.annotation

import com.example.mvc.validator.StringFormatDateTimeValidator
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

//  이 annotaion이 붙은 필드, getter, setter는 이 validator로 검증하겠다는 의미
@Constraint(validatedBy = [StringFormatDateTimeValidator::class])
//  Method대신 Annotation을 통해 Validator구현
@Target(
    AnnotationTarget.FIELD,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented   //  코틀린에서는 붙여야 함
annotation class StringFormatDateTime(
    val pattern: String = "yyyy-MM-dd HH:mm:ss",
    val message: String = "시간 형식 안맞음",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
)