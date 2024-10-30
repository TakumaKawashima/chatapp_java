package in.tech_camp.chatapp.controller;

import in.tech_camp.chatapp.form.RoomForm;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoomsController {

  // ルーム一覧画面の表示
  @GetMapping("/rooms")
  public String index() {
    return "rooms/index"; // ビューの名前
  }

  // ルーム新規作成画面の表示
  @GetMapping("/roomForm")
  public String roomForm(Model model) {
    model.addAttribute("roomForm", new RoomForm());
    return "rooms/roomForm"; // ビューの名前
  }
}
