package com.xabier.desafio.view;

import java.util.List;

public record UserInput(
    String name,
    String email,
    String password,
    List<PhoneInput> phones
    ) {
} 