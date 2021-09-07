package com.olip.rest.webservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonVersioningController {

//    @GetMapping(value = "/person",params = "version=1")
//    public PersonV1 paramV1(){
//        return new PersonV1("Bob Charlie");
//    }

    @GetMapping(value ="/person/produces",produces = "application/v1+json")
    public PersonV2 producesV2(){
        return new PersonV2(new Name("Bob","Charlie"));
    }

    @GetMapping("/v2/person")
    public PersonV2 personV2(){
        return new PersonV2(new Name("Bob","Charlie"));
    }
    @GetMapping(value ="/person",params = "version=2")
    public PersonV2 paramV2(){
        return new PersonV2(new Name("Bob","Charlie"));
    }


}

