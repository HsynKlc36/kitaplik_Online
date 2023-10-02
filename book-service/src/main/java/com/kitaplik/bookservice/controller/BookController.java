package com.kitaplik.bookservice.controller;

import com.kitaplik.bookservice.dto.BookDto;
import com.kitaplik.bookservice.dto.BookIdDto;
import com.kitaplik.bookservice.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@RequestMapping("/v1/book")
@Validated
public class BookController {
  Logger logger= LoggerFactory.getLogger(BookController.class);
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBook(){
         return ResponseEntity.ok(bookService.getAllBooks());//hazır cevap dönmek için bir yapıdır.Eğerki kendi response nesnenizi   oluşturursanız onu da dönebilirsiniz!
    }
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookIdDto> getBookByIsbn(@PathVariable @NotEmpty String isbn){ //validasyonlar için doğrudan bir class oluşturulmadıysa yani sadece 1 parametre varsa ve bunun için class oluşturulmadıysa request class'ı, burada validasyonu doğrudan verebiliriz notEmpty gibi fakat diğer türlü request nesnesi oluşturursak burada @valid annotationunu veriyoruz
        logger.info("Book requested by isbn: "+isbn);
     return ResponseEntity.ok(bookService.findByIsbn(isbn));
    }
    @GetMapping("/book/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable @NotEmpty String id){ //validasyonlar için doğrudan bir class oluşturulmadıysa yani sadece 1 parametre varsa ve bunun için class oluşturulmadıysa request class'ı, burada validasyonu doğrudan verebiliriz notEmpty gibi fakat diğer türlü request nesnesi oluşturursak burada @valid annotationunu veriyoruz
        return ResponseEntity.ok(bookService.findBookDetailsById(id));
    }
}
