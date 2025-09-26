package user_service.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import user_service.model.User;
import user_service.services.UserService;

@RestController
@RequestMapping("/user")
public class Controller {

    private UserService userService;

    @Autowired
    public Controller(UserService userService){
        this.userService = userService;
    }
    
    @GetMapping
    public String root(){
        return "Hi, this is user service!";
    }

    @PostMapping
    public User postUser(@RequestBody User user){
        return userService.saveUser(user);
    }

    @GetMapping("/{user_id}")
    public User getUserById(@PathVariable long user_id){
        return userService.getUserById(user_id);
    }

    @PutMapping("/{user_id}")
    public User updatUserWithOrder(@PathVariable long user_id, @RequestBody long order_id){
        return userService.updateUserWithOrder(user_id, order_id);
    }
}
