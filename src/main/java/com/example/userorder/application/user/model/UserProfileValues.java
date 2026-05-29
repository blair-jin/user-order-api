package com.example.userorder.application.user.model;

import com.example.userorder.domain.user.vo.BirthDate;
import com.example.userorder.domain.user.vo.Email;
import com.example.userorder.domain.user.vo.UserName;

public record UserProfileValues(
        UserName userName,
        BirthDate birthDate,
        Email email
) {
}