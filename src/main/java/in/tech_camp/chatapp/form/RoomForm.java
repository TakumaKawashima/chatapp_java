package in.tech_camp.chatapp.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.List;

import in.tech_camp.chatapp.validation.ValidationPriority1;

@Data
public class RoomForm {
  @NotBlank(message = "Room name can't be blank", groups = ValidationPriority1.class)
  private String name;
  private List<Integer> memberIds;
}
