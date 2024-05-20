package com.vitalika.todo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vitalika.todo.vo.Todo;

import jakarta.persistence.LockModeType;

@Repository
public interface  TodoRepository extends JpaRepository<Todo, Integer> {

	@Lock(LockModeType.PESSIMISTIC_READ)
	@Query("FROM Todo WHERE id = ?1")
	public Optional<Todo> findByIdForUpdate(Integer id);	
}
