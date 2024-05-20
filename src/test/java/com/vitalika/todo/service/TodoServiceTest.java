package com.vitalika.todo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.vitalika.todo.dto.TodoDto;
import com.vitalika.todo.dto.TodoShortDto;
import com.vitalika.todo.repository.TodoRepository;
import com.vitalika.todo.validation.BaseValidationException;
import com.vitalika.todo.vo.Todo;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class TodoServiceTest {

	private TodoService todoService;

	@Mock
	private TodoRepository todoRepository;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		todoService = new TodoService(todoRepository);
	}

	@Test
	public void getAllSuccessfully() {
		List<Todo> rows = new ArrayList<>();
		rows.add(new Todo(1,"NEW","Description 1", LocalDateTime.now(),LocalDateTime.now()));
		when(todoRepository.findAll()).thenReturn(rows);
		
		List<TodoDto> results = todoService.getAll();
		assertEquals(rows.size(), results.size());
	}

	@Test
	public void findByIdSuccessfully() {
		Todo todo = new Todo(1,"NEW","Description 1", LocalDateTime.now(),LocalDateTime.now());
		Optional<Todo> opt = Optional.of(todo);
		when(todoRepository.findById(1)).thenReturn(opt);
		
		TodoDto results = todoService.findById(1);
		assertEquals(results.getStatus(), "NEW");
	}
	
	
	@Test
	public void createTodoSuccessfully() {
		
		TodoShortDto dto = new TodoShortDto("NEW","Description 1");
		Todo todo = new Todo(null,dto.getStatus(),dto.getDescription(),null,null);
		Todo todo1 = new Todo(1,dto.getStatus(),dto.getDescription(),LocalDateTime.now(),LocalDateTime.now());
		
		Mockito.when(todoRepository.save(todo)).thenReturn(todo1);
		
		try {
			TodoDto result = todoService.createTodo(dto);
			assertEquals(result.getStatus(), dto.getStatus());
			assertEquals(result.getDescription(), dto.getDescription());
			
			verify(todoRepository, times(1)).save(todo);
			
		} catch (BaseValidationException e) {
			fail("Validation failed when create Todo called");
		}
	}

	@Test
	public void createTodoFailed() {
		
		TodoShortDto dto = new TodoShortDto("NEW-xx","Description 1");
		Todo todo = new Todo(null,dto.getStatus(),dto.getDescription(),null,null);
		Todo todo1 = new Todo(1,dto.getStatus(),dto.getDescription(),LocalDateTime.now(),LocalDateTime.now());
		
		Mockito.when(todoRepository.save(todo)).thenReturn(todo1);
		
		try {
			todoService.createTodo(dto);
			fail("Validation is missing");
			
		} catch (BaseValidationException e) {
		}
	}
	
	
	@Test
	public void modifyTodoStatusSuccessfully() {
		TodoShortDto dto = new TodoShortDto("RUNNING",null);
		
		Todo todo1 = new Todo(5,"NEW","Description 1",LocalDateTime.now(),LocalDateTime.now());
		Todo todo2 = new Todo(5,"RUNNING","Description 1",LocalDateTime.now(),LocalDateTime.now());
		Optional<Todo> opt = Optional.of(todo1);
		
		Mockito.when(todoRepository.findByIdForUpdate(5)).thenReturn(opt);
		Mockito.when(todoRepository.saveAndFlush(todo2)).thenReturn(todo2);
		
		try {
			TodoDto result = todoService.modifyTodo(5, dto);
			assertEquals(result.getStatus(), "RUNNING");
			assertEquals(result.getDescription(), "Description 1");
			
			verify(todoRepository, times(1)).saveAndFlush(todo2);
			
		} catch (BaseValidationException e) {
			fail("Validation failed when modify Todo called");
		}
		
	}
	
	@Test
	public void modifyTodoDescSuccessfully() {
		TodoShortDto dto = new TodoShortDto(null,"New desc");
		
		Todo todo1 = new Todo(5,"RUNNING","Description 1",LocalDateTime.now(),LocalDateTime.now());
		Todo todo2 = new Todo(5,"RUNNING","New desc",LocalDateTime.now(),LocalDateTime.now());
		Optional<Todo> opt = Optional.of(todo1);
		
		Mockito.when(todoRepository.findByIdForUpdate(5)).thenReturn(opt);
		Mockito.when(todoRepository.saveAndFlush(todo2)).thenReturn(todo2);
		
		try {
			TodoDto result = todoService.modifyTodo(5, dto);
			assertEquals(result.getStatus(), "RUNNING");
			assertEquals(result.getDescription(), "New desc");
			
			verify(todoRepository, times(1)).saveAndFlush(todo2);
			
		} catch (BaseValidationException e) {
			fail("Validation failed when modify Todo called");
		}
		
	}
	
	
	
}
