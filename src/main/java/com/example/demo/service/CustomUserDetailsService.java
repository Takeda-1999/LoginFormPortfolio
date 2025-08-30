package com.example.demo.service;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	
	private final UserMapper userMapper;

	@Override
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
		//DBからユーザー情報を取得
		UserEntity userEntity = userMapper.findByUserEmail(userEmail);
		
		if(userEntity == null) {
			//userEmailの照合が失敗すれば例外を飛ばしてSpringSecurityの内部でキャッチして/user-login?errorが飛ぶ
			throw new UsernameNotFoundException("ユーザーが見つかりませんでした");
		}
		
		//Userクラスに情報を詰め込みパスワードの照合はSpringSecurityが内部で自動照合して認証する
		return new User(
				userEntity.getUserEmail(),
				userEntity.getUserPassword(),
				//ログインしたユーザーに権限を付与する。今回は権限は不要だがコンストラクタで必要のため記述
				AuthorityUtils.createAuthorityList("ROLE_USER")
				);
		
	}

}
