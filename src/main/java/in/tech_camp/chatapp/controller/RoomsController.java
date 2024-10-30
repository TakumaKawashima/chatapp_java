package in.tech_camp.chatapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoomsController {

  @GetMapping("/rooms")
  public String index() {
    return "rooms/index"; // ビューの名前
  }
}
