package com.example.mvc.controller.get

import com.example.mvc.model.http.UserRequest
import org.springframework.web.bind.annotation.*

@RestController //  Rest API로 동작하겠다는 애너테이션, 이걸 붙여야 Controller로써 동작한다.
@RequestMapping("/api") //  http://localhost:8080/api
class GetApiController {

    @GetMapping(path = ["/hello", "/abcd"])   //  (GET) http://localhost:8080/api/hello
    fun hello(): String {
        return "hello kotlin"
    }

    @RequestMapping(method = [RequestMethod.GET], path = ["/request-mapping"])  //  Method등을 직접 지정, 보통 위 방식(@GetMapping)으로 함
    fun requestMapping(): String {
        return "request-mapping"
    }

    //  path 받기
    @GetMapping(path = ["/get-mapping/path-variable/{name}/{age}"])   //  (GET) http://localhost:8080/api/get-mapping/path-variable/steve
    fun pathVariable(
        @PathVariable name: String,
        @PathVariable age: Int,
    ): String {
        println("${name}, $age")
        return "${name}, $age"
    }

    //  path 이름 지정해서 받기
    @GetMapping(path = ["/get-mapping/path-variable2/{name}/{age}"])   //  (GET) http://localhost:8080/api/get-mapping/path-variable/steve
    fun pathVariable2(
        @PathVariable(value = "name") _name: String,
        @PathVariable(name = "age") age: Int,
    ): String {
        val name = ""
        println("${_name}, $age")
        return "${_name}, $age"
    }

    //  http://localhost:8080/api/page?name=steve&age=20...
    @GetMapping("/get-mapping/query-param")
    fun queryParam(
        @RequestParam(value = "name") _name: String,
        @RequestParam age: Int,
    ): String {
        println("${_name}, $age")
        return "${_name}, $age"
    }

    //  object로 받고, 리턴하기
    //  name, age, address, email
    //  return type이 object면 자동으로 json타입으로 반환된다.
    //  이 때 파라미터 명은 선언된 model클래스(여기서는 UserRequest클래스)의 프로퍼티명과 매핑되는데,
    //  코틀린에서 변수명으로 사용 불가능한 하이픈과 같은 문자는 들어가지 않도록 주의해야 한다.
    //  만약 꼭 사용해야 한다면 object형태가 아닌 위 함수의 @RequestParam()을 통해 받도록 한다.
    @GetMapping("/get-mapping/query-param/object")
    fun queryParamObject(
        userRequest: UserRequest,
    ): UserRequest {
        println(userRequest)
        return userRequest
    }

    //  Map으로 받고, 리턴하기
    //  object로 못받은 하이픈이 들어간 파라미터 명도 사용할 수 있다.
    @GetMapping("/get-mapping/query-param/map")
    fun queryParamMap(
        @RequestParam map: Map<String, Any>
    ): Map<String, Any> {
        val phoneNumber = map["phone-number"]
        println(phoneNumber)
        return map
    }
}