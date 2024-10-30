package in.tech_camp.chatapp.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import in.tech_camp.chatapp.entity.RoomEntity;
import in.tech_camp.chatapp.entity.UserRoomEntity;
import java.util.List;

@Mapper
public interface UserRoomRepository {
    @Insert("INSERT INTO user_rooms(user_id, room_id) VALUES(#{user.id}, #{room.id})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(UserRoomEntity userRoomEntity);

    // 特定のユーザーIDに関連するルームを取得
    @Select("SELECT r.* FROM rooms r " +
            "INNER JOIN user_rooms ur ON r.id = ur.room_id " +
            "WHERE ur.user_id = #{userId}")
    List<RoomEntity> findRoomsByUserId(Integer userId);
}
