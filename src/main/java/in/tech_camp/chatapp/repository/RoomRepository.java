package in.tech_camp.chatapp.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import in.tech_camp.chatapp.entity.RoomEntity;

@Mapper
public interface RoomRepository {
  @Select("SELECT * FROM rooms WHERE id = #{id}")
  RoomEntity findById(@Param("id") Integer id);

  @Delete("DELETE FROM rooms WHERE id = #{id}")
  void deleteById(Integer id);

  @Insert("INSERT INTO rooms (name) VALUES (#{name})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insert(RoomEntity room);
}
