package com.example.demo.service;

import com.example.demo.form.UserForm;

public interface UserService {
	
	
	public void save(UserForm userForm);
	
	public UserForm findByUserEmail(String userEmail);
	
	public boolean existsByEmail(String userEmail);
	
	public void checkEmailAndUserUpdate(UserForm userForm);
	
	public void deleteUser(String userEmail);

}
