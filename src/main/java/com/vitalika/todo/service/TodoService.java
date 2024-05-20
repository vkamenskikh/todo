package com.vitalika.todo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.vitalika.todo.dto.TodoDto;
import com.vitalika.todo.dto.TodoShortDto;
import com.vitalika.todo.enumeration.TodoStatusEnum;
import com.vitalika.todo.repository.TodoRepository;
import com.vitalika.todo.validation.BaseValidationException;
import com.vitalika.todo.vo.Todo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TodoService {

	private final TodoRepository todoRepository;

	public List<TodoDto> getAll() {
		List<Todo> rows = todoRepository.findAll();
		List<TodoDto> results = rows.stream().map((p) -> new TodoDto(p)).collect(Collectors.toList());
		return results;
	}

	public TodoDto findById(Integer id) {
		Todo result = todoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		return new TodoDto(result);
	}
	
	public TodoDto createTodo(TodoShortDto dto) throws BaseValidationException {
		Todo todo = new Todo();
		
		TodoStatusEnum status = TodoStatusEnum.lookup(dto.getStatus());
		if (status==null) {
			throw new BaseValidationException("Unknown status: "+dto.getStatus()+". Valid values: "+TodoStatusEnum.getAllAsString()); 
		}
		if (dto.getDescription()==null || dto.getDescription().trim().isEmpty()) {
			throw new BaseValidationException("Description cannot be empty"); 
		}
		todo.setStatus(dto.getStatus());
		todo.setDescription(dto.getDescription());
		Todo sTodo = todoRepository.save(todo);
		
		TodoDto ret = new TodoDto(sTodo);
		return ret;
	}
	
	@Transactional
	public TodoDto modifyTodo(Integer id, TodoShortDto in) throws BaseValidationException {
		Todo row = todoRepository.findByIdForUpdate(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		boolean updated = false;
		if (in.getDescription() !=null && !in.getDescription().isEmpty()) {
			row.setDescription(in.getDescription());
			updated = true;
		}
		if (in.getStatus() !=null && !in.getStatus().isEmpty()) {
			TodoStatusEnum status = TodoStatusEnum.lookup(in.getStatus());
			if (status==null) {
				throw new BaseValidationException("Unknown status: "+in.getStatus()+". Valid values: "+TodoStatusEnum.getAllAsString()); 
			}
			row.setStatus(status.getCode());
			updated = true;
		}
		
		if (updated) {
			row = todoRepository.saveAndFlush(row);
		}
		return new TodoDto(row);
	}

	public void deleteById(Integer id) {
		todoRepository.deleteById(id);
		
	}
	
}
