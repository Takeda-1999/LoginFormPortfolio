package com.example.demo.form;

import java.util.Objects;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.example.demo.entity.UserEntity;

import lombok.Data;

@Data
public class UserForm {
	
	private long userId;
	
	@NotBlank(message="名前を入力してください")
	@Size(max=50, message="50文字以内で入力してください")
	private String userName;
	
	@NotBlank(message="フリガナを入力してください")
	@Size(max=100, message="100文字以内で入力してください")
	private String userFurigana;
	
	@NotBlank(message="メールアドレスを入力してください")
	@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
		    message = "メールアドレスの形式が正しくありません")
	@Size(max=200, message="200文字以内で入力してください")
	private String userEmail;
	
	@NotBlank(message="再確認用メールアドレスを入力してください")
	@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
    		message = "メールアドレスの形式が正しくありません")
	@Size(max=200, message="200文字以内で入力してください")
	private String userConfirmEmail;
	
	@NotBlank(message="パスワードを入力してください")
	@Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8}$", message="パスワードは英字と数字を含む8文字で入力してください")
	private String userPassword;
	
	@NotBlank(message="再確認用のパスワードを入力してください")
	@Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8}$",  message="パスワードは英字と数字を含む8文字で入力してください")
	private String userConfirmPassword;
	
	@AssertTrue(message="パスワードが一致しません")
	public boolean isPassCheck() {
		return Objects.equals(userPassword, userConfirmPassword);
	}
	
	@AssertTrue(message="メールアドレスが一致しません")
	public boolean isEmailCheck() {
		return Objects.equals(userEmail, userConfirmEmail);
	}
	
	//viewにログイン中のユーザー情報を表示させるためにEntityからFormに移し替えるコンストラクタ
	public UserForm(UserEntity userEntity) {
		
		this.userId = userEntity.getUserId();
		this.userName = userEntity.getUserName();
		this.userFurigana = userEntity.getUserFurigana();
		this.userEmail =userEntity.getUserEmail();
	}
	
	//
	
	//Modelの@ModelAttributeで引数なしのコンストラクタが必要なため
	public UserForm() {
		
	}
 
}
