package in.tech_camp.chatapp.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import in.tech_camp.chatapp.entity.UserEntity;
import in.tech_camp.chatapp.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void createUser(UserEntity userEntity){
        String password = userEntity.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        UserEntity user = new UserEntity();
        user.setName(userEntity.getName());
        user.setEmail(userEntity.getEmail());
        user.setPassword(encodedPassword);

        userRepository.insert(user);
    }
}
