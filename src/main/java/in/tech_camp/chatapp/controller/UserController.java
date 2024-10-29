package in.tech_camp.chatapp.controller;

import in.tech_camp.chatapp.form.LoginForm;
import in.tech_camp.chatapp.form.UserEditForm;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import java.util.List;
import org.springframework.stereotype.Controller;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/users/login")
    public String loginForm(Model model){
      model.addAttribute("loginForm", new LoginForm());
      return "users/login";
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, @ModelAttribute("loginForm") LoginForm loginForm,Model model) {
      if (error != null) {
        model.addAttribute("loginError", "メールアドレスかパスワードが間違っています。");
      }
      return "users/login";
    }

    // ユーザー編集フォームを表示
    @GetMapping("/users/{userId}/edit")
    public String editUserForm(@PathVariable("userId") Integer userId, Model model) {
      UserEntity userEntity;
      userEntity = userRepository.findById(userId);

      UserEditForm userForm = new UserEditForm();
      userForm.setId(userEntity.getId());
      userForm.setName(userEntity.getName());
      userForm.setEmail(userEntity.getEmail());

      model.addAttribute("user", userForm);
      return "users/edit";
    }

    // ユーザー情報を更新
    @PostMapping("/users/{userId}/edit")
    public String editUser(@PathVariable("userId") Integer userId, @ModelAttribute("user") @Validated UserEditForm userEditForm, BindingResult result, Model model) {
      if (result.hasErrors()) {
        List<String> errorMessages = result.getAllErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.toList());
        model.addAttribute("errorMessages", errorMessages);
        model.addAttribute("user", userEditForm);
        return "users/edit";
      }
      try {
        UserEntity userEntity = userRepository.findById(userId);
        userEntity.setName(userEditForm.getName());
        userEntity.setEmail(userEditForm.getEmail());
        userRepository.update(userEntity);
      } catch (Exception e) {
        model.addAttribute("errorMessage", e.getMessage());
        model.addAttribute("user", userEditForm);
        return "users/edit";
      }
      return "redirect:/";
    }
}
