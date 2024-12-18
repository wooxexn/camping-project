package com.tz.campon.login.mapper;

import com.tz.campon.login.dto.UserDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user WHERE kakao_id = #{kakao_id}")
    UserDTO findByKakaoId(String kakao_id);

    @Insert("INSERT INTO user (id, email, password, name, phone, kakao_id) VALUES (#{id}, #{email}, #{password}, #{name}, #{phone}, #{kakao_id})")
    void insertUser(UserDTO userDTO);

    @Select("SELECT * FROM user WHERE id = #{id}")
    UserDTO findByUsername(@Param("id") String id);

    @Select("SELECT COUNT(*) > 0 FROM user WHERE id = #{id}")
    boolean existsById(@Param("id") String id);
}
