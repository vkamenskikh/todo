package com.vitalika.todo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.vitalika.todo.dto.TodoDto;
import com.vitalika.todo.dto.TodoShortDto;
import com.vitalika.todo.service.TodoService;
import com.vitalika.todo.validation.BaseValidationException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Todo", description = "Todo application APIs")
@RestController
@RequestMapping("api")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class TodoController {

	private final TodoService todoService;

	@Operation(summary = "Retrieve all Todo rows",
		      description = "Get all Todo records")	
	@GetMapping("/todos")
	public List<TodoDto> getTodoListAll() {
		return todoService.getAll(); 
	}

	@Operation(summary = "Retrieve a Todo by Id",
		      description = "Get a Todo record by specifying its id")	
	@GetMapping("/todos/{id}")
	public TodoDto getTodo(@PathVariable("id") Integer id) {
		return todoService.findById(id);
	}
	
	@Operation(summary = "Create new Todo",
		      description = "Create new Todo with status and description")	
	@PostMapping("/todos")
	public TodoDto createTodo(@RequestBody TodoShortDto tc) {
		try {
			return todoService.createTodo(tc);
		} catch (BaseValidationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@Operation(summary = "Update any field in a Todo record",
		      description = "Update any field in a Todo record by id")
	@PatchMapping("/todos/{id}")
	public TodoDto updateTodo(@PathVariable("id") Integer id, @RequestBody TodoShortDto tc) {
		try {
			return todoService.modifyTodo(id, tc);
		} catch (BaseValidationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@Operation(summary = "Delete a Todo",
		      description = "Delete a Todo record by id")
	@DeleteMapping("/todos/{id}") 
	public void deleteTodo(@PathVariable("id") Integer id) {
		todoService.deleteById(id);
	}
	
}
