package com.example.userorder.application.user.model;

import com.example.userorder.domain.user.vo.BirthDate;
import com.example.userorder.domain.user.vo.Email;
import com.example.userorder.domain.user.vo.UserName;

import java.time.LocalDate;

public record UserProfileValues(
        UserName userName,
        BirthDate birthDate,
        Email email
) {
    public static UserProfileValues of(String name, LocalDate birthDate, String email) {
        return new UserProfileValues(
                name != null ? UserName.of(name) : null,
                birthDate != null ? BirthDate.of(birthDate) : null,
                email != null ? Email.of(email) : null
        );
    }

    public boolean isEmpty() {
        return userName == null
                && birthDate == null
                && email == null;
    }
}