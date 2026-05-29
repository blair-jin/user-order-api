package com.example.userorder.domain.user;

import com.example.userorder.domain.common.BaseTimeEntity;
import com.example.userorder.domain.user.vo.BirthDate;
import com.example.userorder.domain.user.vo.Email;
import com.example.userorder.domain.user.vo.UserName;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Table(name = "user_profiles")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserProfile extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false, unique = true)
    private Long userId;

    private String name;
    private LocalDate birthDate;
    private String email;

    private UserProfile(Long userId, String name, LocalDate birthDate, String email) {
        this.userId = Objects.requireNonNull(userId);
        this.name = name;
        this.birthDate = birthDate;
        this.email = email;
    }

    public static UserProfile create(Long userId, UserName name, BirthDate birthDate, Email email) {
        validateProfileNotEmpty(name, birthDate, email);
        
        return new UserProfile(
                userId,
                name != null ? name.value() : null,
                birthDate != null ? birthDate.value() : null,
                email != null ? email.value() : null
        );
    }

    public void updateProfile(UserName name, BirthDate birthDate, Email email) {
        if (name != null) {
            this.name = name.value();
        }

        if (birthDate != null) {
            this.birthDate = birthDate.value();
        }

        if (email != null) {
            this.email = email.value();
        }
    }

    private static void validateProfileNotEmpty(UserName name, BirthDate birthDate, Email email) {
        if (name == null && birthDate == null && email == null) {
            throw new IllegalArgumentException("USER_PROFILE_EMPTY");
        }
    }
}