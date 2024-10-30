package in.tech_camp.chatapp.entity;

import lombok.Data;

@Data
public class UserRoomEntity {
  private Long id;

  // @ManyToOne(fetch = FetchType.EAGER)
  private UserEntity user;

  // @ManyToOne(fetch = FetchType.EAGER)
  private RoomEntity room;

  // 以下デバック時に使用するコード
  @Override
  public String toString() {
    return "UserRoomEntity{" +
        "id=" + id +
        ", userId=" + (user != null ? user.getId() : null) + // ユーザーIDのみ表示
        ", roomId=" + (room != null ? room.getId() : null) + // 部屋IDのみ表示
        '}';
  }
  // デバッグコードここまで
}
