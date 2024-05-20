package com.vitalika.todo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.vitalika.todo.controller.TodoController;

@SpringBootTest
class TodoApplicationTests {

	@Autowired
	private TodoController controller;
	
	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();		
	}

}
