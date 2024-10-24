package in.tech_camp.chatapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import in.tech_camp.chatapp.form.UserForm;

@Controller
public class UserController {
  @GetMapping("/users/sign_up")
  public String showSignUp(Model model) {
    model.addAttribute("UserForm", new UserForm());
    return "users/sign_up";
  }
}
