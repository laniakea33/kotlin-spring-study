package com.example.todo.todo.config

import com.example.todo.todo.database.TodoDatabase
import com.example.todo.todo.service.TodoService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

//  App이 실행될 때의 Bean설정
@Configuration
class AppConfig {

    @Bean(initMethod = "init")  //  Bean생성시 실행할 메소드명을 지정할 수 있음
    fun todoDatabase(): TodoDatabase {
        return TodoDatabase()
    }
}