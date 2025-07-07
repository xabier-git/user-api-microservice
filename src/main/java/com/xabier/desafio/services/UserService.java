package com.xabier.desafio.services;

import java.util.List;

import com.xabier.desafio.model.User;
import com.xabier.desafio.view.UserView;

public interface  UserService {

    public UserView addUser(User user);
    public void deleteUser(Long id) ;
    public UserView updateUser(User user) ;
    public List<UserView> getAllUsers() ;
    public UserView getUserById(Long id);

}
