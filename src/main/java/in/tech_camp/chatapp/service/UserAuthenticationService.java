package in.tech_camp.chatapp.service;

import org.springframework.security.core.userdetails.UserDetails;
import in.tech_camp.chatapp.CustomUserDetails;
import in.tech_camp.chatapp.entity.UserEntity;
import in.tech_camp.chatapp.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserAuthenticationService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    UserEntity userEntity = userRepository.findByEmail(email);
    if (userEntity == null) {
      throw new UsernameNotFoundException("User not found with email: " + email);
    }
    return new CustomUserDetails(userEntity);
  }
}
