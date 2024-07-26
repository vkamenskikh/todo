package com.vitalika.todo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vitalika.todo.repository.BookRepository;
import com.vitalika.todo.vo.Book;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookService {
	private final BookRepository bookRepository;
    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }
 
    // Getting a specific book by category from collection
    public List<Book> getBookByCategory(String category){
        List<Book> book = bookRepository.findByCategory(category);
        return book;
    }
 
    // Getting a specific book by book id from collection
    public Book getBookByBookId(long bookId){
        Book book = bookRepository.findByBookId(bookId);
        return book;
    }
     
      // Adding/inserting a book into collection
    public Book addBook(long id,String isbnNumber, String bookName,String category) {
        Book book = new Book();
        book.setCategory(category);
        book.setBookId(id);
        book.setBookName(bookName);
        book.setIsbnNumber(isbnNumber);
        return bookRepository.save(book);
    }
   
    // Delete a book from collection
    public int deleteBook(long bookId){
        Book book = bookRepository.findByBookId(bookId);
        if(book != null){
            bookRepository.delete(book);
            return 1;
        }
        return -1;
    }
}
