package com.vitalika.todo.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.vitalika.todo.dto.TodoDto;
import com.vitalika.todo.dto.TodoShortDto;
import com.vitalika.todo.service.TodoService;

@WebMvcTest(TodoController.class)
public class TodoControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TodoService todoService;
	
	private TodoDto fullDto = new TodoDto(1,"NEW","Description 1",LocalDateTime.now(),LocalDateTime.now());

	@Test
	public void testGetAll() throws Exception {
		List<TodoDto> rows = new ArrayList<>();
		
		Mockito.when(todoService.getAll()).thenReturn(rows);
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/api/todos")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void testGetTodoById() throws Exception {
		
		Mockito.when(todoService.findById(1)).thenReturn(fullDto);
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/api/todos/1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	
	@Test
	public void testCreateTodo() throws Exception {
		TodoShortDto shortDto = new TodoShortDto("NEW","Description 1");
		
		Mockito.when(todoService.createTodo(shortDto)).thenReturn(fullDto);
		
		mockMvc.perform(MockMvcRequestBuilders
				.post("/api/todos")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\r\n"
						+ "  \"status\": \"NEW\",\r\n"
						+ "  \"description\": \"Description 1\"\r\n"
						+ "}"))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testUpdateTodo() throws Exception {
		TodoShortDto shortDto = new TodoShortDto("NEW",null);
		
		Mockito.when(todoService.modifyTodo(1, shortDto)).thenReturn(fullDto);
		
		mockMvc.perform(MockMvcRequestBuilders
				.patch("/api/todos/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\r\n"
						+ "  \"status\": \"NEW\"\r\n"
						+ "}"))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testDeleteTodo() throws Exception {
		
		mockMvc.perform(MockMvcRequestBuilders
				.delete("/api/todos/1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}

	
}
