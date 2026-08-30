package io.github.blairjeon.userorderapi.application.user.reader;

import io.github.blairjeon.userorderapi.domain.user.User;
import io.github.blairjeon.userorderapi.domain.user.UserProfile;
import io.github.blairjeon.userorderapi.domain.user.vo.LoginId;
import io.github.blairjeon.userorderapi.domain.user.vo.UserName;
import io.github.blairjeon.userorderapi.dto.user.UserNameResult;
import io.github.blairjeon.userorderapi.exception.CONFLICT.DuplicateLoginIdException;
import io.github.blairjeon.userorderapi.exception.NOT_FOUND.UserNotFoundException;
import io.github.blairjeon.userorderapi.exception.NOT_FOUND.UserProfileNotFoundException;
import io.github.blairjeon.userorderapi.repository.user.UserProfileRepository;
import io.github.blairjeon.userorderapi.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserReader {
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    public void validateDuplicateLoginId(LoginId loginId){
        if(userRepository.existsByLoginId(loginId.value())){
            throw new DuplicateLoginIdException();
        }
    }

    public User getUserByLoginId(LoginId loginId){
        return userRepository.findByLoginId(loginId.value())
                .orElseThrow(UserNotFoundException::new);
    }

    public User getUserById(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    public Map<Long, String> getUserNameByIds(List<Long> userIds){
        List<UserNameResult> list = userProfileRepository.findUserNamesByUserIds(userIds);
        return list.stream().collect(Collectors.toMap(UserNameResult::userId, UserNameResult::userName));
    }

    public UserName getUserNameById(Long userId){
        String result = userProfileRepository.findUserNameByUserId(userId)
                .orElseThrow(UserNotFoundException::new);

        return UserName.of(result);
    }

    public UserProfile getUserProfileByUserId(Long userId){
        return userProfileRepository.findByUserId(userId)
                .orElseThrow(UserProfileNotFoundException::new);
    }
}