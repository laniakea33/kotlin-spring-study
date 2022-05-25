package com.example.todo.todo.controller.api.todo

import com.example.todo.todo.model.http.TodoDto
import com.example.todo.todo.service.TodoService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/todo")
@Api(description = "일정관리")
class TodoApiController(
    val todoService: TodoService
) {

    @GetMapping(path = [""])
    @ApiOperation(value = "일정확인", notes = "일정확인 GET API")
    fun read(
        @ApiParam(name = "index")
        @RequestParam(required = false) index: Int?
    ): ResponseEntity<Any?> {
        return index?.let {
            todoService.read(it)
        }?.let {
            return ResponseEntity.ok(it)
        } ?: run {
            return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header(HttpHeaders.LOCATION, "/api/todo/all").build()
        }
    }

    @GetMapping(path = ["/all"])
    fun readAll(): ResponseEntity<MutableList<TodoDto>> {
        return ResponseEntity.ok(todoService.readAll())
    }

    @PostMapping(path = [""])
    fun create(@Valid @RequestBody todoDto: TodoDto): ResponseEntity<TodoDto> {
        return ResponseEntity.ok(todoService.create(todoDto))
    }

    @PutMapping(path = [""])
    fun update(@Valid @RequestBody todoDto: TodoDto): ResponseEntity<TodoDto> {
        return if (todoDto.index != null && todoService.read(todoDto.index!!) != null) {
            val result = todoService.update(todoDto)
            ResponseEntity.ok(result)
        } else {
            val result = todoService.update(todoDto)
            ResponseEntity.status(201).body(result)
        }
    }

    @DeleteMapping(path = ["/{index}"])
    fun delete(@PathVariable(name = "index") _index: Int): ResponseEntity<Any> {
        if (!todoService.delete(_index)) {
            return ResponseEntity.status(500).build()
        }
        return ResponseEntity.ok().build()
    }
}