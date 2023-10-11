package br.com.bravewolfdevelop.todolist.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {


    @Autowired
    private IUserRespository userRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel) {
       var user = this.userRepository.findByUsername(userModel.getUsername());

       if (user != null) {
           System.out.println("User already exists");
           return ResponseEntity.status(400).body("User already exists");
       }
       var userCreated = this.userRepository.save(userModel);
       var passwordHash = BCrypt.withDefaults()
               .hashToString(12, userModel.getPassword().toCharArray());

       return ResponseEntity.status(201).body(userCreated);
    }
}
