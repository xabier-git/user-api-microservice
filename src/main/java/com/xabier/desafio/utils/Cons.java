package com.xabier.desafio.utils;

public abstract class Cons {

 public static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
 public static final String PHONE_PATTERN = "^[0-9]{1,3}-[0-9]{6,12}$";  
 public static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$"; 
 public static final String NAME_PATTERN = "^[A-Za-zÀ-ÿ\\s]{2,50}$"; 

}
