package in.tech_camp.chatapp.form;

import in.tech_camp.chatapp.validation.ValidationPriority1;
import in.tech_camp.chatapp.validation.ValidationPriority2;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserEditForm {
    private Integer id;
    @NotBlank(message = "Nickname can't be blank",groups = ValidationPriority1.class)
    private String name;
    @NotBlank(message = "Email can't be blank",groups =ValidationPriority1.class)
    @Email(message = "Email should be valid", groups = ValidationPriority2.class)
    private String email;
}
