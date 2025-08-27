package com.example.demo.entity;

import com.example.demo.form.UserForm;

import lombok.Data;

@Data
public class UserEntity {
	
	private Long userId;
	private String userName;
	private String userFurigana;
	private String userEmail;
	private String userPassword;
	
	public UserEntity(UserForm userForm) {
		
		
		this.userName = userForm.getUserName();
		this.userFurigana = userForm.getUserFurigana();
		this.userEmail = userForm.getUserEmail();
		this.userPassword = userForm.getUserPassword();
	}
	
	public UserEntity() {
		
	}
	

}
