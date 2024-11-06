package in.tech_camp.chatapp.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;

import in.tech_camp.chatapp.entity.MessageEntity;

@Mapper
public interface MessageRepository {
  @Select("SELECT * FROM messages")
  List<MessageEntity> findAll();

  @Select("SELECT * FROM messages WHERE room_id = #{roomId}")
      @Results(value = {
      @Result(property = "createdAt", column = "created_at"),
      @Result(property = "user", column = "user_id",
              one = @One(select = "in.tech_camp.chatapp.repository.UserRepository.findById", fetchType = FetchType.LAZY))
  })
  List<MessageEntity> findByRoomId(Integer roomId);

  @Insert("INSERT INTO messages(content, image, created_at, user_id, room_id) VALUES(#{content}, #{image}, #{createdAt}, #{user.id}, #{room.id})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insert(MessageEntity messageEntity);

  // メッセージを削除するメソッド
  @Delete("DELETE FROM messages WHERE id = #{id}")
  void deleteById(@Param("id") Integer id);

  // メッセージの数をカウントするメソッド、テストで使用
  @Select("SELECT COUNT(*) FROM messages")
  int count();
}
