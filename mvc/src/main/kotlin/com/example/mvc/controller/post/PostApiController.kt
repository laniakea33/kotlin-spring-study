package com.example.mvc.controller.post

import com.example.mvc.model.http.UserRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class PostApiController {

    @PostMapping("/post-mapping")
    fun postMapping(

    ): String{
        return "post-mapping ㅎ2"
    }

    //  GetApiController에 이미 /api/request-mapping가 있으나, method가 다르므로 충돌이 일어나지 않는다.
    //  동일한 주소가 있는지는 늘 주의할 것
    @RequestMapping(
        method = [RequestMethod.POST],
        path = ["/request-mapping"],
    )
    fun requestMapping(

    ): String {
        return "request-mapping ㅎ2"
    }

    //  object mapper
    //  object <-> json
    //  스프링이 기본적으로 사용하는 중임
    //  body의 json을 object로 받을 수 있다.
    @RequestMapping(
        "/post-mapping/object"
    )
    fun objectMapping(
        @RequestBody userRequest: UserRequest,
    ): UserRequest {
        println(userRequest)
        return userRequest
    }
}
