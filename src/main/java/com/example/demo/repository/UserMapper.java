package com.example.demo.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.UserEntity;

@Mapper
public interface UserMapper {
	
	void save(UserEntity userEntity);
	
	UserEntity findByUserEmail(String userEmail);
	
	void userUpdate(UserEntity userEntity);
	
	//引数が2つなので@ParamでSQLと直接バインドする
	int existsByEmailForUpdate(@Param("userId")Long userId, @Param("userEmail")String userEmail);
	
	void deleteUser(String userEmail);

}
