package com.example.mvc.controller.exception

import com.example.mvc.model.http.UserRequest
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.util.LinkedMultiValueMap

@WebMvcTest //  모든 스프링 부트의 기능이 아닌, Mvc에 필요한 기능만 사용하도록 설정
@AutoConfigureMockMvc
class ExceptionApiControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun helloTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/exception/hello")
        ).andExpect (
            MockMvcResultMatchers.status().isOk //  200값이 오기를 기대함
//            MockMvcResultMatchers.status().isBadRequest //  400값이 오기를 기대함
        ).andExpect {
            MockMvcResultMatchers.content().string("hello")
        }.andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun getTest() {
        val queryParams = LinkedMultiValueMap<String, String>().apply {
            add("name", "steve")
            add("age", "20")
        }

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/exception").queryParams(queryParams)
        ).andExpect(
            MockMvcResultMatchers.status().isOk
        ).andExpect(
            MockMvcResultMatchers.content().string("steve 20")
        ).andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun getFailedTest() {
        val queryParams = LinkedMultiValueMap<String, String>().apply {
            add("name", "steve")
            add("age", "9")
        }

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/exception").queryParams(queryParams)
        ).andExpect(
            MockMvcResultMatchers.status().isBadRequest
        ).andExpect(
            MockMvcResultMatchers.content().contentType("application/json")
        ).andExpect(
            //  json객체의 result_code:FAIL임을 기대함
            MockMvcResultMatchers.jsonPath("\$.result_code").value("fail")
        ).andExpect(
            //  json배열인 errors의 첫번째 필드명이 age임을 기대함
            MockMvcResultMatchers.jsonPath("\$.errors[0].field").value("age")
        ).andExpect(
            //  json배열인 errors의 첫번째 필드값이 9임을 기대함
            MockMvcResultMatchers.jsonPath("\$.errors[0].value").value("9")
        ).andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun postTest() {
        val userRequest = UserRequest().apply {
            this.name = "steve"
            this.age = 10
            this.phoneNumber = "010-2367-4474"
            this.address = "구로구 가리봉동"
            this.email = "laniakea33@naver.com"
            this.createdAt = "2021-10-09 11:22:33"
        }

        val json = jacksonObjectMapper().writeValueAsString(userRequest)
        println(json)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/exception")
                .content(json)
                .contentType("application/json")
                .accept("application/json")
        ).andExpect(
            MockMvcResultMatchers.status().isOk
        ).andExpect(
            MockMvcResultMatchers.jsonPath("\$.name").value("steve")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("\$.age").value(10)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("\$.phoneNumber").value("010-2367-4474")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("\$.address").value("구로구 가리봉동")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("\$.email").value("laniakea33@naver.com")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("\$.createdAt").value("2021-10-09 11:22:33")
        )
    }

    @Test
    fun postFailTest() {
        val userRequest = UserRequest().apply {
            this.name = "steve"
            this.age = -1
            this.phoneNumber = "010-2367-4474"
            this.address = "구로구 가리봉동"
            this.email = "laniakea33@naver.com"
            this.createdAt = "2021-10-09 11:22:33"
        }

        //  Data class를 JSON으로 매핑한다.
        val json = jacksonObjectMapper().writeValueAsString(userRequest)
        println(json)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/exception")
                .content(json)
                .contentType("application/json")
                .accept("application/json")
        ).andExpect(
            MockMvcResultMatchers.status().isBadRequest
        ).andDo(MockMvcResultHandlers.print())
    }
}