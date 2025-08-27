package com.example.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.UserEntity;
import com.example.demo.form.UserForm;
import com.example.demo.repository.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final PasswordEncoder passwordEncoder;
	private final UserMapper userMapper;
	

	//DBにユーザー情報登録
	@Override
	public void save(UserForm userForm) {
		
		UserEntity userEntity = new UserEntity(userForm);
		String encoderPassword = passwordEncoder.encode(userEntity.getUserPassword());
		userEntity.setUserPassword(encoderPassword);
		userMapper.save(userEntity);
	}
	
	//UserEmailを元にログイン中のユーザー情報を持ってくる
	@Override
	public UserForm findByUserEmail(String userEmail) {
		
		//userEmailを元にDBからユーザー情報を取得
		UserEntity userEntity = userMapper.findByUserEmail(userEmail);
		
		//Entityの情報をFormに詰める
		UserForm userForm = new UserForm(userEntity);
		
		return userForm;
	}
	
	
	//ユーザーが送信したメールアドレスがDBに登録済みかどうか判定する
	@Override
	public boolean existsByEmail(String userEmail) {
		
		return userMapper.findByUserEmail(userEmail) != null;
	}
	
	//このメソッド内ではSELECTとUPDATEと2つのDB操作があり、まとめて実行するため@Transactionalをつける
	@Transactional
	@Override
	public void checkEmailAndUserUpdate(UserForm userForm) {
		
		//更新対象のユーザーを除外してメールアドレスが重複していないか確認
		UserEntity userEntity = new UserEntity(userForm);
		userEntity.setUserId(userForm.getUserId());
		int count = userMapper.existsByEmailForUpdate(userEntity.getUserId(), userEntity.getUserEmail());
		
		if(count > 0) {
			
			throw new IllegalArgumentException("このメールアドレスは使用されています");
		}

		//問題なければパスワードをハッシュ値にしてDB登録
		userEntity.setUserPassword(passwordEncoder.encode(userForm.getUserPassword()));
		
		userMapper.userUpdate(userEntity);

	}
	
	//principalからログイン中の情報を持ってくる
	@Override
	public void deleteUser(String userEmail) {
		
		userMapper.deleteUser(userEmail);
	}
}


