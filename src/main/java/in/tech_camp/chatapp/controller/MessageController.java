package in.tech_camp.chatapp.controller;

import in.tech_camp.chatapp.CustomUserDetails;
import in.tech_camp.chatapp.repository.MessageRepository;
import in.tech_camp.chatapp.repository.RoomRepository;
import in.tech_camp.chatapp.repository.UserRepository;
import in.tech_camp.chatapp.repository.UserRoomRepository;

import org.springframework.stereotype.Controller;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import java.sql.Timestamp;

import in.tech_camp.chatapp.entity.MessageEntity;
import in.tech_camp.chatapp.entity.RoomEntity;
import in.tech_camp.chatapp.entity.UserEntity;
import in.tech_camp.chatapp.form.MessageForm;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class MessageController {
  private final UserRepository userRepository;
  private final RoomRepository roomRepository;
  private final UserRoomRepository userRoomRepository;
  private final MessageRepository messageRepository;

  @GetMapping("/rooms/{roomId}/messages")
  public String showMessages(@PathVariable("roomId") Integer roomId,
      @AuthenticationPrincipal CustomUserDetails currentUser, Model model) {
    UserEntity user = userRepository.findById(currentUser.getId());
    if (user != null) {
      List<RoomEntity> roomList = userRoomRepository.findRoomsByUserId(user.getId());
      model.addAttribute("rooms", roomList);
      model.addAttribute("user", user);
    }

    model.addAttribute("messageForm", new MessageForm());

    RoomEntity room = roomRepository.findById(roomId);
    model.addAttribute("room", room);

    // メッセージを取得してモデルに追加
    List<MessageEntity> messages = messageRepository.findByRoomId(roomId); // ルームIDでメッセージを取得
    model.addAttribute("messages", messages); // メッセージをモデルに追加
    return "messages/index";
  }

  @PostMapping("/rooms/{roomId}/messages")
  public String saveMessage(@PathVariable("roomId") Integer roomId, @ModelAttribute("messageForm") MessageForm messageForm, BindingResult result, @AuthenticationPrincipal CustomUserDetails currentUser, Model model) {
    messageForm.validateMessage(result);
    if (result.hasErrors()) {
        return "redirect:/rooms/" + roomId + "/messages";
    }

    MessageEntity message = new MessageEntity();
    message.setContent(messageForm.getContent());
    message.setCreatedAt(new Timestamp(System.currentTimeMillis()));

    UserEntity user = userRepository.findById(currentUser.getId());
    RoomEntity room = roomRepository.findById(roomId);
    if (user != null && room != null) {
        message.setUser(user);
        message.setRoom(room);
        messageRepository.insert(message); // メッセージをデータベースに保存
    }

    return "redirect:/rooms/" + roomId + "/messages"; // メッセージを送信した後にリダイレクト
  }
}
