package com.example.mvc.controller.page

import com.example.mvc.model.http.UserRequest
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

/*
@Controller
특정 Html페이지를 내릴 때 사용하는 Controller
return값으로 "main.html"을 내리면 문자열이 아니라 해당 파일을 반환하게 되는데,
이것이 @RestController와의 차이점이다.

@RestController이 붙으면 이걸로 동작함.

@Controller가 붙은 컨트롤러에서 RestController와 같은 object형태의 응답을 주고 싶으면 메서드에 @ResponseBody를 붙이면 된다.
 */
@Controller
class PageController {

    //  http://localhost:8080/main
    @GetMapping("/main")
    fun main(): String {    //  text "main.html"
        return "main.html"
    }

    @ResponseBody
    @GetMapping("/test")
    fun response(): UserRequest {
        return UserRequest().apply {
            this.name = "steve"
        }
        //  return "main.html"
    }
}