package in.tech_camp.chatapp.controller;

import in.tech_camp.chatapp.entity.RoomEntity;
import in.tech_camp.chatapp.entity.UserEntity;
import in.tech_camp.chatapp.entity.UserRoomEntity;
import in.tech_camp.chatapp.form.RoomForm;
import in.tech_camp.chatapp.repository.RoomRepository;
import in.tech_camp.chatapp.repository.UserRepository;
import in.tech_camp.chatapp.repository.UserRoomRepository;
import in.tech_camp.chatapp.validation.ValidationOrder;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Controller;
import java.util.List;
import org.springframework.ui.Model;
import java.util.stream.Collectors;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.core.Authentication;
import in.tech_camp.chatapp.CustomUserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class RoomsController {

  private final UserRepository userRepository;
  private final RoomRepository roomRepository;
  private final UserRoomRepository userRoomRepository;

  // ルーム一覧画面の表示
  @GetMapping("/")
  public String index(@AuthenticationPrincipal CustomUserDetails currentUser, Model model) {
    UserEntity user = userRepository.findById(currentUser.getId());
    if (user != null) {
      List<RoomEntity> roomList = userRoomRepository.findRoomsByUserId(user.getId());
      model.addAttribute("rooms", roomList);
      model.addAttribute("user", user);
    }

    return "rooms/index"; // ビューの名前
  }

  // ルーム新規作成画面の表示
  @GetMapping("/roomForm")
  public String roomForm(@ModelAttribute("roomForm") RoomForm roomForm, Authentication authentication, Model model) {
    String currentUserEmail = authentication.getName();
    Integer currentUserId = userRepository.findByEmail(currentUserEmail).getId();
    List<UserEntity> users = userRepository.findAllExcept(currentUserId);
    model.addAttribute("users", users);
    return "rooms/roomForm"; // ビューの名前
  }

  @PostMapping("/rooms")
  public String createRoom(@ModelAttribute("room") @Validated(ValidationOrder.class) RoomForm roomForm,
      BindingResult bindingResult, Authentication authentication, Model model) {
    if (bindingResult.hasErrors()) {
      List<String> errorMessages = bindingResult.getAllErrors().stream()
          .map(DefaultMessageSourceResolvable::getDefaultMessage)
          .collect(Collectors.toList());
      String currentUserEmail = authentication.getName();
      Integer currentUserId = userRepository.findByEmail(currentUserEmail).getId();
      List<UserEntity> users = userRepository.findAllExcept(currentUserId);
      model.addAttribute("users", users);
      model.addAttribute("errorMessages", errorMessages);
      return "rooms/roomForm"; // フォームを再表示
    }

    RoomEntity roomEntity = new RoomEntity();
    roomEntity.setName(roomForm.getName());
    //返り値にidがなし
    roomRepository.insert(roomEntity);

    List<Integer> memberIds = roomForm.getMemberIds();
    // 各参加者を中間テーブルに関連付ける
    for (Integer userId : memberIds) {
      UserEntity userEntity = userRepository.findById(userId);
      // if (userEntity != null) {
      //   userEntity.getRooms().add(roomEntity); // UserEntityに部屋を追加
      //   userRepository.save(userEntity); // 変更を保存
      // }
      UserRoomEntity userRoomEntity = new UserRoomEntity();
      userRoomEntity.setRoom(roomEntity);
      userRoomEntity.setUser(userEntity);
      userRoomRepository.insert(userRoomEntity);
    }

    return "redirect:/";
  }
}
