package com.kitaplik.libraryservice.client;


import com.kitaplik.libraryservice.exception.ExceptionMessage;
import com.kitaplik.libraryservice.exception.BookNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class RetreiveMessageErrorDecoder implements ErrorDecoder {//feign client ile alınan cevaplarda hata fırlatılırsa burada handling yaparız!! ErrorDecoder implement edilmelidir!!

    private final ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        ExceptionMessage message = null;
        try (InputStream body = response.body().asInputStream()){
            message = new ExceptionMessage((String) response.headers().get("date").toArray()[0],
                    response.status(),
                    HttpStatus.resolve(response.status()).getReasonPhrase(),//status kodun string halini döner
                    IOUtils.toString(body, StandardCharsets.UTF_8),//Stream nesnelerini Stringe çevirir (commons-io) kütüphanesi ile
                    response.request().url());

        } catch (IOException exception) {
            return new Exception(exception.getMessage());
        }
        switch (response.status()) {
            case 404://burada aslında book-service deki hata durumunda dönecek koda göre case'i 404 yazdık cünkü book-service bulunmaz ve hata fırlatırsa feign client ile hata yukarıdaki class ile yakalanır ve dönen response'a göre bir mesaj oluştururuz ardından ise burada handling edebilmemiz yani library-service de hatayı yakalayıp controller'a geri dönmeden doğrudan hatayı responseEntity olarak dönebilmek için globalhandler ile yakalamamız gerekiyor onun içinde burada hata fırlatmamız gerekir!!
                throw new BookNotFoundException(message);//burada bir hata fırlatmalıyımki buradaki global exception handler ile yakalayayım!!
            default:
                return errorDecoder.decode(methodKey, response);//diğer durumlarda errordecoder'ın defauld decode metodu çalışsın deriz
        }
    }
}