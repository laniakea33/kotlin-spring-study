package com.example.todo.todo.repository

import com.example.todo.todo.config.AppConfig
import com.example.todo.todo.database.Todo
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime

//  테스트할 클래스만 로드할 수 있도록 지정해 줄 것
@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [TodoRepositoryImpl::class, AppConfig::class])
class TodoRepositoryTest {

    @Autowired
    lateinit var todoRepositoryImpl: TodoRepositoryImpl

    //  각 테스트케이스 실행 전에 @BeforeEach가 실행된다.
    @BeforeEach
    fun before() {
        todoRepositoryImpl.todoDatabase.init()
    }

    @Test
    fun saveTest() {
        val todo = Todo().apply {
            title = "테스트 일정"
            description = "테스트 디스크립션"
            schedule = LocalDateTime.now()
        }

        val result = todoRepositoryImpl.save(todo)

        Assertions.assertEquals(1, result?.index)
        Assertions.assertNotNull(result?.createdAt)
        Assertions.assertNotNull(result?.updatedAt)
        Assertions.assertEquals("테스트 디스크립션", result?.description)
        Assertions.assertEquals("테스트 일정", result?.title)
    }

    @Test
    fun saveAllTest() {
        val todoList = mutableListOf(
            Todo().apply {
                title = "테스트 일정"
                description = "테스트 디스크립션"
                schedule = LocalDateTime.now()
            },
            Todo().apply {
                title = "테스트 일정"
                description = "테스트 디스크립션"
                schedule = LocalDateTime.now()
            },
            Todo().apply {
                title = "테스트 일정"
                description = "테스트 디스크립션"
                schedule = LocalDateTime.now()
            },
        )

        val result = todoRepositoryImpl.saveAll(todoList)
        Assertions.assertEquals(true, result)
    }

    @Test
    fun findOneTest() {
        val todoList = mutableListOf(
            Todo().apply {
                title = "테스트 일정1"
                description = "테스트 디스크립션1"
                schedule = LocalDateTime.now()
            },
            Todo().apply {
                title = "테스트 일정2"
                description = "테스트 디스크립션2"
                schedule = LocalDateTime.now()
            },
            Todo().apply {
                title = "테스트 일정3"
                description = "테스트 디스크립션3"
                schedule = LocalDateTime.now()
            },
        )

        todoRepositoryImpl.saveAll(todoList)

        val result = todoRepositoryImpl.findOne(2)

        Assertions.assertNotNull(result)
        Assertions.assertEquals("테스트 일정2", result?.title)
    }


    @Test
    fun findAllTest() {
        val todoList = mutableListOf(
            Todo().apply {
                title = "테스트 일정1"
                description = "테스트 디스크립션1"
                schedule = LocalDateTime.now()
            },
            Todo().apply {
                title = "테스트 일정2"
                description = "테스트 디스크립션2"
                schedule = LocalDateTime.now()
            },
            Todo().apply {
                title = "테스트 일정3"
                description = "테스트 디스크립션3"
                schedule = LocalDateTime.now()
            },
        )

        todoRepositoryImpl.saveAll(todoList)

        val result = todoRepositoryImpl.findAll()
        Assertions.assertEquals(3, result.size)
    }

    @Test
    fun updateTest() {
        val todo = Todo().apply {
            title = "테스트 일정"
            description = "테스트 디스크립션"
            schedule = LocalDateTime.now()
        }

        val insertedTodo = todoRepositoryImpl.save(todo)

        val todo2 = Todo().apply {
            this.index = insertedTodo?.index
            title = "업데이트 일정"
            description = "업데이트 테스트 디스크립션"
            schedule = LocalDateTime.now()
        }

        val result = todoRepositoryImpl.save(todo2)

        Assertions.assertNotNull(result)
        Assertions.assertEquals(insertedTodo?.index, todo2.index)
        Assertions.assertEquals("업데이트 일정", todo2.title)
        Assertions.assertEquals("업데이트 테스트 디스크립션", todo2.description)
    }

    @Test
    fun deleteTest() {
        val todo = Todo().apply {
            title = "테스트 일정"
            description = "테스트 디스크립션"
            schedule = LocalDateTime.now()
        }

        val insertedTodo = todoRepositoryImpl.save(todo)

        val result = insertedTodo?.index?.let {
            todoRepositoryImpl.delete(it)
        }

        Assertions.assertNotNull(result)
        Assertions.assertEquals(true, result)
    }

}