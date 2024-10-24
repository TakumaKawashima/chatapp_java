package in.tech_camp.chatapp.controller;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import java.util.List;
import org.springframework.stereotype.Controller;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import in.tech_camp.chatapp.validation.ValidationOrder;

import in.tech_camp.chatapp.entity.UserEntity;
import in.tech_camp.chatapp.form.UserForm;
import in.tech_camp.chatapp.repository.UserRepository;
import in.tech_camp.chatapp.service.UserService;

@Controller
public class UserController {
  private final UserRepository userRepository;
  private final UserService userService;

  public UserController(UserRepository userRepository, UserService userService) {
    this.userRepository = userRepository;
    this.userService = userService;
  }
  @GetMapping("/users/sign_up")
  public String showSignUp(Model model) {
    model.addAttribute("userForm", new UserForm());
    return "users/sign_up";
  }

    @PostMapping("/users/sign_up")
    public String createUser(@ModelAttribute("userForm") @Validated(ValidationOrder.class) UserForm userForm,
        BindingResult result, Model model) {
      userForm.validatePasswords(result);
      if (userRepository.existsByEmail(userForm.getEmail())) {
        result.rejectValue("email", "error.user", "Email already exists");
      }

      if (result.hasErrors()) {
        List<String> errorMessages = result.getAllErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.toList());
        model.addAttribute("errorMessages", errorMessages);
        model.addAttribute("user", userForm);
        return "users/sign_up";
      }
      try {
        UserEntity userEntity = new UserEntity();

        userEntity.setName(userForm.getName());
        userEntity.setEmail(userForm.getEmail());
        userEntity.setPassword(userForm.getPassword());

        userService.createUser(userEntity);
      } catch (Exception e) {
        model.addAttribute("errorMessage", e.getMessage());
        model.addAttribute("user", userForm);
        return "users/sign_up";
      }
      return "redirect:/";
    }
}
