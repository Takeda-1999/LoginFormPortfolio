package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.service.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity //webアプリに全体にSpringSecurityを有効化する宣言
public class SecurityConfig {
	
	private final CustomUserDetailsService customUserDetailsService;

	@Bean
	public PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { //HttpSecurityはSpringSecurityの設定を行うためのクラス
		
		http
			//認可設定
			.authorizeHttpRequests(auth -> auth
					.requestMatchers("/", "/top", "/user-regist","/regist-complete", "/user-complete","/login", "/css/**").permitAll() //認証不要
					.anyRequest().authenticated() //それ以外は認証必須
					)
			//ログイン認証設定
			.formLogin(login  -> login
					.loginPage("/user-login") //自作ログインページを指定
					.loginProcessingUrl("/user-login-auth") //POSTリクエスト用。認証処理を行う
					.usernameParameter("userEmail")
					.passwordParameter("userPassword")
					.defaultSuccessUrl("/mypage", true)//ログイン成功でGETリクエストで/mypageへ
					.failureUrl("/user-login?error") //ログイン失敗時にはlogin.htmlに戻りエラーメッセージを出す
					.permitAll() //ログイン処理に必要な一連のURLを認証不要するため
					)
			//ログアウト設定
			.logout(logout -> logout
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login?logout")
				.permitAll()
				);
		
		return http.build();
			
		
	}
	
	@Bean
	public AuthenticationManager authManager(HttpSecurity http) throws Exception {
		
		//AuthticationManagerBuilder.classはユーザーの認証方法を構築するための設定組み立てクラス
		return http.getSharedObject(AuthenticationManagerBuilder.class)
				/*カスタムしたcustomUserDetailsServiceクラスをSpringに渡すので、ますはuserDetailsServiceに渡している
				  DB検索の仕組みをセット*/
				.userDetailsService(customUserDetailsService)
				//パスワード検証方法をセット
				.passwordEncoder(passwordEncoder())
				.and()
				//上記の設定を元にAuthenticationManagerを生成して返している
				.build();
	}

}
