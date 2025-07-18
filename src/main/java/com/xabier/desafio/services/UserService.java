package com.xabier.desafio.services;

import java.util.List;

import com.xabier.desafio.view.UserInput;
import com.xabier.desafio.view.UserView;

public interface  UserService {

    public UserView addUser(UserInput user);
    public void deleteUser(String id) ;
    public UserView updateUser(String id, UserInput userInput);
    public List<UserView> getAllUsers() ;
    public UserView getUserById(String id);

}
