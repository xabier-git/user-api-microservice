package com.xabier.desafio.utils;

public abstract class Cons {
 // Regular expressions for validation patterns

 public static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

 public static final String PHONE_PATTERN = "^[0-9]{1,3}-[0-9]{6,12}$";  
 // Password must contain at least one uppercase letter, one lowercase letter, one digit, and be at least 8 characters long
 //public static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$"; 
 // Password must contain at least one uppercase letter, one lowercase letter, and at least two digits, and be at least 6 characters long
 public static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[a-z])(?=(?:.*\\d){2,}).{6,}$";
 public static final String NAME_PATTERN = "^[A-Za-zÀ-ÿ\\s]{2,50}$"; 

}
