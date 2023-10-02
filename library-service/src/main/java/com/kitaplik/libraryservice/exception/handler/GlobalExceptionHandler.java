package com.kitaplik.libraryservice.exception.handler;

import com.kitaplik.libraryservice.exception.BookNotFoundException;
import com.kitaplik.libraryservice.exception.LibraryNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(LibraryNotFoundException.class)
    public ResponseEntity<?> handle(LibraryNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(BookNotFoundException.class)//burada artık feign clientten gelen ve retreiveMessageErrorDecoder da yakalayıp düzenleyip ardından da fırtlattığımız hata mesajını burada yakalarız!
    public ResponseEntity<?> handle(BookNotFoundException exception){
        return new ResponseEntity<>(exception.getExceptionMessage(), HttpStatus.resolve(exception.getExceptionMessage().status()));
    }
}
