package com.olip.rest.webservices.helloworld;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
public class HelloWorldController {
   private final MessageSource messageSource;

    public HelloWorldController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping("/hello-world")
    public  String helloWorld(){
       return "Hello World!!";
   }

    @GetMapping("/hello-world-bean")
    public  HelloWorldBean helloWorldbean(){
        return new HelloWorldBean("Hello World!!");
    }
    @GetMapping("/hello-world/path-variable/{name}")
    public  HelloWorldBean helloWorldPathVariable(@PathVariable String name){
        return new HelloWorldBean(String.format("Hello World!!, %s",name));
    }
    @GetMapping("/hello-world-internationalized")
    public  String helloWorldInternationalized(@RequestHeader(name ="ACCEPT_LANGUAGE",required = false) String lc){
        System.out.println(lc);
        return messageSource.getMessage("good.morning.message",null,"Default Message", LocaleContextHolder.getLocale()) ;
    }
}
