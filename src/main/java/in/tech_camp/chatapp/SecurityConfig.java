package in.tech_camp.chatapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .csrf(AbstractHttpConfigurer::disable)
      .authorizeHttpRequests(authorizeRequests -> authorizeRequests
        .requestMatchers("/css/**", "/images/**", "/", "/users/sign_up","/users/login").permitAll()
        .requestMatchers(HttpMethod.POST, "/users/sign_up").permitAll()
        .anyRequest().authenticated())
        .formLogin(login -> login
          .loginProcessingUrl("/login")
          .loginPage("/users/login")
          .defaultSuccessUrl("/", true)
          .failureUrl("/login?error")
            .usernameParameter("email")
            .passwordParameter("password")
            .permitAll())
        .logout(logout -> logout
          .logoutSuccessUrl("/loginForm"));

    return http.build();
  }
}
