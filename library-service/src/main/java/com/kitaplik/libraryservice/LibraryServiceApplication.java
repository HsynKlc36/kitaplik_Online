package com.kitaplik.libraryservice;

import com.kitaplik.libraryservice.client.RetreiveMessageErrorDecoder;
import feign.Logger;
import feign.codec.ErrorDecoder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients//feign client'ı bildiririz!!
public class LibraryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryServiceApplication.class, args);
	}
/*
	FEIGN CLIENT ERROR HANDLING(yapabilmemizi sağlar yani decoder'ı kullanabilmemizi)
	@Bean
	public ErrorDecoder errorDecoder(){//bunun ile decoderdaki hatayı yakalayıp handling ederiz decoder'ı kullanabilmek için yani feign client ile diğer servise istek atıp hata aldığımızda decoder'a gelip sonrasında da oradaki hatayı fırlatıp handler da o hatayı yakalayabilmek için buradaki @bean'i oluşturmamız gerekir!
		return new RetreiveMessageErrorDecoder();
	}
	@Bean
	Logger.Level feignLoggerLevel(){
		return Logger.Level.FULL;
	}*/

}
