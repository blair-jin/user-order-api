package io.github.blairjeon.userorderapi.repository.user;

import io.github.blairjeon.userorderapi.domain.user.UserProfile;
import io.github.blairjeon.userorderapi.dto.user.UserNameResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByUserId(Long userId);

    @Query("SELECT up.userName FROM UserProfile up WHERE up.userId = :userId")
    Optional<String> findUserNameByUserId(Long userId);

    @Query("""
    SELECT new io.github.blairjeon.userorderapi.dto.user.UserNameResult(
        up.userId,
        up.userName
    )
    FROM UserProfile up
    WHERE up.userId IN :userIds
    """)
    List<UserNameResult> findUserNamesByUserIds(List<Long> userIds);
}