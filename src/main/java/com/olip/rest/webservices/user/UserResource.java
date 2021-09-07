package com.olip.rest.webservices.user;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;

@RestController
public class UserResource {

  private final UserDaoService userDaoService;

    public UserResource(UserDaoService userDaoService) {
        this.userDaoService = userDaoService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userDaoService.findAll();
    }
    @GetMapping("/users/{id}")
    public EntityModel<User> getUser(@PathVariable Integer id){
        User user =userDaoService.findOne(id);
        if(user==null){
            throw new UserNotFoundException("id"+id);
        }
        EntityModel<User> model = EntityModel.of(user);
        WebMvcLinkBuilder linkToUsers = linkTo(methodOn(this.getClass()).getAllUsers());
        model.add(linkToUsers.withRel("all-user"));
        return model;
    }

    @PostMapping("/users")
    public ResponseEntity createUser(@Validated  @RequestBody User user){
//        User user1 = new User(user.getId(),"Olip",new Date());
//        user.setId(null);
//        user.setName("Olip");
//        user.setBirthDate(new Date());
       User savedUser= userDaoService.save(user);
       URI location =  ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
       return ResponseEntity.created(location).build();

//        return userDaoService.save(user);

    }
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id){
        User user = userDaoService.deleteById(id);

        if(user==null){
            throw  new UserNotFoundException("id-"+id);
        }
    }
}
