package com.example.mvc.controller.response

import com.example.mvc.model.http.UserRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/*
ResponseEntity : body의 내용을 object로 설정할때 유용하게 쓰인다.
 */
@RestController
@RequestMapping("/api/response")
class ResponseApiController {

    //  1. get  4xx
    //  GET localhost:8080/api/response?age=10
    @GetMapping("")
    fun getMapping(
        @RequestParam("age", required = false) age: Int?,    //  required는 기본값이 true
    ): ResponseEntity<String> {
        age?.let {
            //  1. age < 20 -> 400 error
            if (age < 20) {
                return ResponseEntity.status(400).body("age값은 20보다 커야 합니다.")
            }
        } ?: run {
            //  0. age == null -> 400 error
            return ResponseEntity.status(400).body("age값이 누락되었습니다.")
        }

        return ResponseEntity.ok("OK")
    }

    //  2. post 200
    @PostMapping("")
    fun postMapping(
        @RequestBody userRequest: UserRequest?
    ): ResponseEntity<Any> {
        return ResponseEntity.status(200).body(userRequest) //  object mapper -> object -> json
    }

    //  3. put 201
    @PutMapping("")
    fun putMapping(
        @RequestBody userRequest: UserRequest?
    ): ResponseEntity<UserRequest> {
        //  1. 기존 데이터가 없어서 새로 생성함
        return ResponseEntity.status(HttpStatus.CREATED).body(userRequest)
    }

    //  4. delete 500
    @DeleteMapping("/{id}")
    fun deleteMapping(
        @PathVariable id: Int
    ): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
    }
}