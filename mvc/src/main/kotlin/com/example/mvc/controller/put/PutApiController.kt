package com.example.mvc.controller.put

import com.example.mvc.model.http.UserRequest
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

/*
POST와 PUT을 처리하는 방식은 사실상 동일하다.
다만 그 용도에 차이가 있음.
 */
@RestController
@RequestMapping("/api")
class PutApiController {

    @PutMapping("/put-mapping")
    fun putMapping(): String {
        return "put-mapping"
    }

    @RequestMapping(method = [RequestMethod.PUT], path = ["/request-mapping"])
    fun requestMapping(): String {
        return "request-mapping - put-method"
    }

    //  Bean 검증
    @PutMapping(path = ["/put-mapping-object"])
    //  UserRequest Bean에 대한 부분적인 검증이 필요할 땐 @Valid를 검증이 필요한 해당 파라미터에 설정해 준 후,
    //  Data class에서 검증한다.
    fun putMappingObject(
        @Valid @RequestBody userRequest: UserRequest,
        bindingResult: BindingResult,   //  유효성 검증 결과를 받음
    ): ResponseEntity<String> {
        if (bindingResult.hasErrors()) {    //  검증 결과 에러가 있다면 이렇게 처리할 수 있음.
            //  500 error
            val msg = StringBuilder()
            bindingResult.allErrors.forEach {
                val field = it as FieldError
                val message = it.defaultMessage
                msg.append("${field.field} : $message\n")
            }
            return ResponseEntity.badRequest().body(msg.toString())
        }

        return ResponseEntity.ok("")

//        return UserResponse().apply {
//            this.result = Result().apply {
//                this.resultCode = "OK"
//                this.resultMessage = "성공"
//            }
//        }.apply {
//            this.description = "ㅎ2ㅎ2ㅎ2"
//        }.apply {
//            val userList = mutableListOf<UserRequest>()
//            userList.add(userRequest)
//            userList.add(UserRequest().apply {
//                this.name = "a"
//                this.age = 10
//                this.email = "a@gmail.com"
//                this.address = "a address"
//                this.phoneNumber = "01010101010"
//            })
//            userList.add(UserRequest().apply {
//                this.name = "b"
//                this.age = 10
//                this.email = "b@gmail.com"
//                this.address = "b address"
//                this.phoneNumber = "1010101010"
//            })
//            this.userRequest = userList
//        }
    }
}