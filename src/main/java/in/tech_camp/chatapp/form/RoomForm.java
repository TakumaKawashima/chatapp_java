package in.tech_camp.chatapp.form;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

// Define ValidationPriority1 interface
import in.tech_camp.chatapp.validation.ValidationPriority1;

@Data
public class RoomForm {
  @NotBlank(message = "Room Name can't be blank", groups = ValidationPriority1.class)
  private String name;

  private List<Integer> memberIds;
}
