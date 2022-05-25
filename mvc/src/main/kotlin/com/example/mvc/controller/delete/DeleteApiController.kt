package com.example.mvc.controller.delete

import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

/*
POST와 PUT처럼
GET과 DELETE는 그 처리형태가 비슷하다.
*/

@RestController
@RequestMapping("/api")
//  spring-boot-starter-validation 사용 설정
@Validated
class DeleteApiController {

    @DeleteMapping("/delete-mapping")
    fun deleteMapping(
        @RequestParam(name = "name") _name: String,
        @NotNull(message = "age값이 누락되었습니다.")    //  spring-boot-starter-validation
        @Min(value = 20, message = "age는 20보다 커야 합니다.") //  spring-boot-starter-validation
        @RequestParam(name = "age") _age: Int,
    ): String {
        println(_name)
        println(_age)
        return "$_name, $_age"
    }

    @DeleteMapping(path = ["/delete-mapping-path/name/{name}/age/{age}"])
    fun deleteMappingPath(
        @Size(min = 2, max = 5, message = "name의 길이는 2~5")    //  spring-boot-starter-validation
        @NotNull
        @PathVariable(value = "name") _name: String,
        @NotNull(message = "age값이 누락되었습니다.")    //  spring-boot-starter-validation
        @Min(value = 20, message = "age는 20보다 커야 합니다.") //  spring-boot-starter-validation
        @PathVariable(name = "age") _age: Int,
    ): String {
        return "$_name, $_age"
    }
}