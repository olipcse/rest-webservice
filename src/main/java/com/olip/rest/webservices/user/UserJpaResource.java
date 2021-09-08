package com.olip.rest.webservices.user;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserJpaResource {

  private final UserDaoService userDaoService;
  private final UserRepository userRepository;

    public UserJpaResource(UserDaoService userDaoService,UserRepository userRepository) {
        this.userDaoService = userDaoService;
        this.userRepository =userRepository;
    }

    @GetMapping("jpa/users")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    @GetMapping("jpa/users/{id}")
    public EntityModel<User> getUser(@PathVariable Integer id){
        Optional<User> user =userRepository.findById(id);

        if(user.isEmpty()){
            System.out.println(user.get());
            throw new UserNotFoundException("id"+id);
        }
        EntityModel<User> model = EntityModel.of(user.get());
        WebMvcLinkBuilder linkToUsers = linkTo(methodOn(this.getClass()).getAllUsers());
        model.add(linkToUsers.withRel("all-user"));
        return model;
    }

    @PostMapping("jpa/users")
    public ResponseEntity createUser(@Validated  @RequestBody User user){
//        User user1 = new User(user.getId(),"Olip",new Date());
//        user.setId(null);
//        user.setName("Olip");
//        user.setBirthDate(new Date());
       User savedUser= userRepository.save(user);
       URI location =  ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
       return ResponseEntity.created(location).build();

//        return userDaoService.save(user);

    }
    @DeleteMapping("jpa/users/{id}")
    public void deleteUser(@PathVariable Integer id){
        userRepository.deleteById(id);


    }
}
