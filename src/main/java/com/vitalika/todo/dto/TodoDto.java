package com.vitalika.todo.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.vitalika.todo.vo.Todo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoDto {
	private int id;
	
	public TodoDto(Todo in) {
		this.id = in.getId();
		this.status = in.getStatus();
		this.description = in.getDescription();
		this.createdAt = in.getCreatedAt();
		this.updatedAt = in.getUpdatedAt();
	}
	
    private String status;
    private String description;
    
    @JsonInclude(Include.NON_NULL)
    private LocalDateTime createdAt;
    
    @JsonInclude(Include.NON_NULL)
    private LocalDateTime updatedAt;
	
    
}
