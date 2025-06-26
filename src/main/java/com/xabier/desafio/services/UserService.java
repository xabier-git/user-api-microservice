package com.xabier.desafio.services;

import java.util.List;

import com.xabier.desafio.exception.BussinesException;
import com.xabier.desafio.model.User;
import com.xabier.desafio.view.UserView;

public interface  UserService {

    public UserView addUser(User user) throws BussinesException;
    public void deleteUser(Long id) throws BussinesException;
    public UserView updateUser(User user) throws BussinesException;
    public List<UserView> getAllUsers() throws BussinesException;
    public UserView getUserById(Long id) throws BussinesException;
    public UserView getUserByEmail(String email) throws BussinesException;
}
