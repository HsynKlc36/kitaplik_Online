package com.kitaplik.libraryservice.service;

import com.kitaplik.libraryservice.client.BookServiceClient;
import com.kitaplik.libraryservice.dto.LibraryDto;
import com.kitaplik.libraryservice.exception.LibraryNotFoundException;
import com.kitaplik.libraryservice.model.Library;
import com.kitaplik.libraryservice.repository.LibraryRepository;
import com.kitaplik.libraryservice.request.AddBookRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibraryService {
    private  final LibraryRepository libraryRepository;
    private final BookServiceClient bookServiceClient;//feign client inject ettik ayrıca LibraryServiceApplication içerisine de bunun bir bean olarak görünmesi için @EnableFeignClients ekledik!

    public LibraryService(LibraryRepository libraryRepository, BookServiceClient bookServiceClient) {
        this.libraryRepository = libraryRepository;
        this.bookServiceClient = bookServiceClient;
    }
    public LibraryDto getAllBooksInLibraryById(String id){
        Library library=libraryRepository.findById(id)
                .orElseThrow(()->new LibraryNotFoundException("library could not found by id: "+id));
        LibraryDto libraryDto = new LibraryDto(library.getId(),
                library.getUserBook()
                        .stream()
                        .map(book -> bookServiceClient.getBookById(book).getBody())
                        .collect(Collectors.toList()));
        return libraryDto;
    }
    public LibraryDto createLibrary(){
        Library newlibrary=libraryRepository.save(new Library());
        return  new LibraryDto(newlibrary.getId());
    }
    public void addBookToLibrary(AddBookRequest request){
         String boookId=bookServiceClient.getBookByIsbn(request.getIsbn()).getBody().getBookId();// burada feign client ile book-service'den veriyi almaya çalışır fakat bulamazsa hata dönmelidir(bookNotFoundException) exception handling ile yapılır diğer bir konu ise (Create a default value - change the request path) yani handling yapılabileceği gibi default bir value verip öyle bir path'e yönlendirme yapılabilir!İkisi aynı anda yappılamaz ya process'i sonlandırıp hata fırlatırsınız ya da default bir value yaratıp  process(işlem)'i devam ettirirsiniz!Sürecinizde iki yol da varsa default bir value oluşturup process'i devam ettirmek hata fırlatmayı ezer!

         Library library=libraryRepository.findById(request.getId())
                 .orElseThrow(()->new LibraryNotFoundException("library could not found by request: "+request));

         library.getUserBook()
                 .add(boookId);
         libraryRepository.save(library);
    }

    public List<String> getAllLibraries() {
        return libraryRepository.findAll()
                .stream()
                .map(l->l.getId())
                .collect(Collectors.toList());
    }
}
