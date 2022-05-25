package com.example.todo.todo.repository

import com.example.todo.todo.database.Todo

interface TodoRepository {

    fun save(todo: Todo): Todo?
    fun saveAll(todos: MutableList<Todo>): Boolean
    fun delete(index: Int): Boolean

    fun findOne(index: Int): Todo?
    fun findAll(): MutableList<Todo>
}