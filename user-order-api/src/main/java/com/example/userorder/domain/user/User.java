package com.example.userorder.domain.user;

import com.example.userorder.domain.common.BaseTimeEntity;
import com.example.userorder.domain.user.vo.LoginId;
import com.example.userorder.domain.user.vo.Password;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    private User(String loginId, String password, Role role) {
        this.loginId = loginId;
        this.password = password;
        this.role = role == null ? Role.USER : role;
    }

    public static User create(LoginId loginId, Password password) {
        Objects.requireNonNull(loginId, "loginId must not be null");
        Objects.requireNonNull(password, "password must not be null");

        return new User(
                loginId.value(),
                password.value(),
                Role.USER
        );
    }
}