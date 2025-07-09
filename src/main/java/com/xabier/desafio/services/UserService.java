package com.xabier.desafio.services;

import java.util.List;

import com.xabier.desafio.view.UserInput;
import com.xabier.desafio.view.UserView;

public interface  UserService {

    public UserView addUser(UserInput user, String token);
    public void deleteUser(Long id) ;
    public UserView updateUser(Long id, UserInput userInput);
    public List<UserView> getAllUsers() ;
    public UserView getUserById(Long id);

}
