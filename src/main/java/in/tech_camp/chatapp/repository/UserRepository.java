package in.tech_camp.chatapp.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import in.tech_camp.chatapp.entity.UserEntity;

@Mapper
public interface UserRepository {
    @Select("SELECT * FROM users WHERE id = #{id}")
    UserEntity findById(Integer id);

    @Select("SELECT * FROM users WHERE email = #{email}")
    UserEntity findByEmail(String email);

    @Select("SELECT EXISTS(SELECT 1 FROM users WHERE email = #{email})")
    boolean existsByEmail(String email);

    // ログインユーザー自身以外のEmailの重複チェック
    @Select("SELECT COUNT(*) > 0 FROM users WHERE email = #{email} AND id != #{userId}")
    boolean existsByEmailExcludingCurrent(String email, Integer userId);

    // 特定のユーザーIDを除外して全ユーザーを取得
    @Select("SELECT * FROM users WHERE id <> #{excludedId}")
    List<UserEntity> findAllExcept(Integer excludedId);

    @Insert("INSERT INTO users (name, email, password) VALUES (#{name}, #{email}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(UserEntity user);

    @Update("UPDATE users SET name = #{name}, email = #{email} WHERE id = #{id}")
    void update(UserEntity user);
}
