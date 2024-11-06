package in.tech_camp.chatapp.entity;

import java.sql.Timestamp;
import lombok.Data;

@Data
public class MessageEntity {
  private Integer id;

  private String content;

  private String image;

  private Timestamp createdAt;

  // @ManyToOne
  private UserEntity user;

  // @ManyToOne(cascade = CascadeType.REMOVE)
  private RoomEntity room;
}
