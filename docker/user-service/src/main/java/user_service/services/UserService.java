package user_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import user_service.model.User;
import user_service.repository.UserRepository;


@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public User getUserById(long id){
        return userRepository.findById(id)
            .orElseThrow(()->new RuntimeException("User not found with id: " + id));
    }

    public User updateUserWithOrder(long user_id, long order_id){
        User user = userRepository.findById(user_id)
            .orElseThrow(()->new RuntimeException("User not found with id: " + user_id));
        user.getOrders().add(order_id);
        return userRepository.save(user);
    }
}
