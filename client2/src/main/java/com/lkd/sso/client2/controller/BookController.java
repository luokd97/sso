package com.lkd.sso.client2.controller;
import com.lkd.sso.client2.domain.Book;
import com.lkd.sso.client2.domain.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

//@RestController
@Controller
//@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @ResponseBody
    @GetMapping("/findAll")
    public String findAll() {
        String s = "Book List:";
        Iterator<Book> books = bookRepository.findAll().iterator();
        while(books.hasNext()){
            s+="<br>"+books.next().toString();
        }
        return s+"<br><a href=\"/createBook\">New A Book</a>";
    }

    @RequestMapping("/createBook")
    public String createBook(){
        return "create_book";
    }

    @RequestMapping("/bookManagement")
    public String showManagement(){
        return "book_management";
    }

    @PostMapping("/book")
//    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String create(Book book) {
        return bookRepository.save(book).toString()+"<br><a href=\"/findAll\">Show All Books</a>";
    }

    public Book create111(@RequestBody Book book) {
        return bookRepository.save(book);
    }
//    @GetMapping("/title/{bookTitle}")
//    public List findByTitle(@PathVariable String bookTitle) {
//        return bookRepository.findByTitle(bookTitle);
//    }
//
//    @GetMapping("/{id}")
//    public Book findOne(@PathVariable Long id) {
//        return bookRepository.findById(id)
//                .orElseThrow(BookNotFoundException::new);
//    }
//
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public Book create(@RequestBody Book book) {
//        return bookRepository.save(book);
//    }
//
//    @DeleteMapping("/{id}")
//    public void delete(@PathVariable Long id) {
//        bookRepository.findById(id)
//                .orElseThrow(BookNotFoundException::new);
//        bookRepository.deleteById(id);
//    }
//
//    @PutMapping("/{id}")
//    public Book updateBook(@RequestBody Book book, @PathVariable Long id) {
//        if (book.getId() != id) {
//            throw new BookIdMismatchException();
//        }
//        bookRepository.findById(id)
//                .orElseThrow(BookNotFoundException::new);
//        return bookRepository.save(book);
//    }
}
