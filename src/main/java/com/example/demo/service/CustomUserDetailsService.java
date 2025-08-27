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
			//SpringSecurityが標準で用意している例外クラス
			throw new UsernameNotFoundException("ユーザーが見つかりませんでした");
		}
		
		//Userクラスに情報を詰め込みSpringSecurityが内部で自動照合して認証する
		return new User(
				userEntity.getUserEmail(),
				userEntity.getUserPassword(),
				//ログインしたユーザーに権限を付与する
				AuthorityUtils.createAuthorityList("ROLE_USER")
				);
		
	}

}
