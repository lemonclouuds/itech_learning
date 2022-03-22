package com.itech.learning.service;

import com.itech.learning.domain.Rating;
import com.itech.learning.domain.User;
import com.itech.learning.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.List;

import static com.itech.learning.service.ExceptionMessage.USER_WITH_ID_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException(String.format(USER_WITH_ID_NOT_FOUND, userId)));
    }

    public List<Rating> getUserRates(Long userId) {
        User user = findById(userId);
        return user.getRates();
    }

    @Transactional
    public void deleteByIdIn(Collection<Long> ids) {
        userRepository.deleteByIdIn(ids);
    }
}
