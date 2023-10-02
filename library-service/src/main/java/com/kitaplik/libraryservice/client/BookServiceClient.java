package com.kitaplik.libraryservice.client;

import com.kitaplik.libraryservice.dto.BookDto;
import com.kitaplik.libraryservice.dto.BookIdDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "book-service",path = "/v1/book")//controller mapping
public interface BookServiceClient {//ulaşmak istediğimiz servis adıyla ulaşmak best practice'dir

  Logger logger= LoggerFactory.getLogger(BookServiceClient.class);
    @GetMapping("/isbn/{isbn}")
    @CircuitBreaker(name = "getBookByIsbnCircutBreaker",fallbackMethod = "getBookFallback")//feign clientte book-service'den hata gelirse library-service de hata fırlatmamak için default kitap oluşturulabilir ya da başka bir path'a yönlendirme yapabilmek için kullanılır!
     ResponseEntity<BookIdDto> getBookByIsbn(@PathVariable(value = "isbn")  String isbn);

    default ResponseEntity<BookIdDto> getBookFallback(String isbn,Exception exception){//buradaki default ile yukarıdaki fallback isminde metot yapıp hata alınması durumunda hata fırlatmak istemediğimizde buradaki metot sonucundaki return'u kullanıcıya döneriz! ya da başka bir path'a yönlendirme yapabilirsiniz kurguya göre değişir!
      logger.info("book not found by isbn "+isbn+ ", returning default BookDto Object");
      return  ResponseEntity.ok(new BookIdDto("default-book","default-isbn"));
    }
    @GetMapping("/book/{bookId}")
     ResponseEntity<BookDto> getBookById(@PathVariable(value = "bookId")  String bookId);
    @CircuitBreaker(name = "getBookByIdCircutBreaker",fallbackMethod = "getBookByIdFallback")
    default ResponseEntity<BookDto> getBookByIdFallback(String bookId,Exception exception){
        logger.info("book not found by id "+bookId+ ", returning default BookDto Object");
        return  ResponseEntity.ok(new BookDto(new BookIdDto("default-book","default-isbn")));
    }
}
//burada aslında liibrary service'den book-service'e ulaşmak isteriz ve ulaşmak istediğimiz adresin controller daki metotları ile aslında çağırırız sanki o url 'e dışardan rest ile istekte bulunuyor gibi