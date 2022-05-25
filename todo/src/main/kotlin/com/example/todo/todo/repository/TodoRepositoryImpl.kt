package com.example.todo.todo.repository

import com.example.todo.todo.database.Todo
import com.example.todo.todo.database.TodoDatabase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class TodoRepositoryImpl: TodoRepository {

    @Autowired
    lateinit var todoDatabase: TodoDatabase

    override fun save(todo: Todo): Todo? {
        return todo.index?.let { index ->
            //  update
            findOne(index)?.apply {
                title = todo.title
                description = todo.description
                schedule = todo.schedule
                updatedAt = LocalDateTime.now()
            }
        } ?: run {
            todo.apply {
                index = ++todoDatabase.index
                createdAt = LocalDateTime.now()
                updatedAt = LocalDateTime.now()
            }.run {
                todoDatabase.todoList.add(this)
                this
            }
        }
    }

    override fun saveAll(todos: MutableList<Todo>): Boolean {
        return try {
            todos.forEach {
                save(it)
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun delete(index: Int): Boolean {
        return findOne(index)?.let {
            todoDatabase.todoList.remove(it)
            true
        } ?: run {
            false
        }
    }

    override fun findOne(index: Int): Todo? {
        return todoDatabase.todoList.firstOrNull {
            it.index == index
        }
    }

    override fun findAll(): MutableList<Todo> {
        return todoDatabase.todoList
    }
}