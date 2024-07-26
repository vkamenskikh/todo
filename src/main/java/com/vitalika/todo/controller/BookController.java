package com.vitalika.todo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vitalika.todo.service.BookService;
import com.vitalika.todo.vo.Book;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Book", description = "Book application APIs")
@RequiredArgsConstructor
@RequestMapping("api")
@CrossOrigin(origins = "*")
@RestController
public class BookController {
	private final BookService bookService;

	@GetMapping("/getAllBooks")
    @ResponseBody
    public List<Book> getBooks(){
        return bookService.getAllBooks();
    }
 
	@GetMapping("/getBook")
    @ResponseBody
    public List<Book> getBook(@RequestParam("category") String category){
        return bookService.getBookByCategory(category);
    }
 
	@GetMapping("/getBookById")
    @ResponseBody
    public Book getBookById(@RequestParam("bookId") long bookId){
        return bookService.getBookByBookId(bookId);
    }
 
	@PostMapping("/addBook")
    @ResponseBody
    public String addBook(@RequestParam("bookId") long bookId,@RequestParam("isbnNumber") String isbnNumber,
                          @RequestParam("bookName") String bookName,
                          @RequestParam("category") String category){
        if(bookService.addBook(bookId,isbnNumber,bookName,category) != null){
            return "Book got  Added Successfully";
        }else{
            return "Something went wrong !";
        }
    }
   
	@DeleteMapping("/deleteBook")
    @ResponseBody
    public String deleteBook(@RequestParam("bookId") int bookId){
        if(bookService.deleteBook(bookId) == 1){
            return "Book got  Deleted Successfully";
        }else{
            return "Something went wrong !";
        }
    }	
}
