package com.vitalika.todo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.vitalika.todo.vo.Book;

public interface BookRepository extends MongoRepository<Book,String> {
    List<Book> findByCategory(String category);
    Book findByBookId(long bookId);
}
