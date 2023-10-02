package com.kitaplik.bookservice;

import com.kitaplik.bookservice.model.Book;
import com.kitaplik.bookservice.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableEurekaClient //uygulamada ayağa kalkan eurekayı bul ve onun endpointine register ol.
public class BookServiceApplication implements CommandLineRunner {
	private final BookRepository bookRepository;

	public BookServiceApplication(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(BookServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Book book1=new Book("Yüzüklerin efendisi",1960,"J.R.R Tolkien","hsyn","234aksjdl");
		Book book2=new Book("harry Potter",1997,"J.K.R Tolkien","klc","234ASD");
		Book book3=new Book("Dünyanın gözü",2000,"Robert Jordan","hsyn","456MMm");
		List<Book> bookList= bookRepository.saveAll(Arrays.asList(book1,book2,book3));
		System.out.println(bookList);

	}
}
