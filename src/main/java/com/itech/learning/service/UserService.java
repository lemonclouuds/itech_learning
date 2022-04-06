package com.itech.learning.service;

import com.itech.learning.domain.Rating;
import com.itech.learning.domain.User;
import com.itech.learning.domain.dto.RatingDto;
import com.itech.learning.domain.dto.UserDto;
import com.itech.learning.exception.EntityAlreadyExistsException;
import com.itech.learning.helper.MapperHelper;
import com.itech.learning.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.itech.learning.constants.ExceptionMessage.RATING_WITH_ID_NOT_FOUND;
import static com.itech.learning.constants.ExceptionMessage.USER_WITH_ID_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserService {
    private static ModelMapper modelMapper = new ModelMapper();

    private final UserRepository userRepository;

    public List<UserDto> getAll() {
        return MapperHelper.mapList(userRepository.findAll(), UserDto.class);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException(String.format(USER_WITH_ID_NOT_FOUND, userId)));
    }

    public UserDto findDtoById(Long userId) {
        return modelMapper.map(findById(userId), UserDto.class);
    }

    @Transactional
    public UserDto create(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        if (!userRepository.existsById(userDto.getId())) {
            user.setRates(new ArrayList<>());
            userRepository.save(user);
        } else {
            throw new EntityAlreadyExistsException(String.format("User with id[%d] already exists",
                    userDto.getId()));
        }
        return userDto;
    }

    @Transactional
    public UserDto update(Long userId, UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        user.setId(userId);
        if (userRepository.existsById(userId)) {
            userRepository.save(user);
        } else {
            throw new EntityNotFoundException(String.format(USER_WITH_ID_NOT_FOUND, userId));
        }
        return userDto;
    }

    @Transactional
    public void deleteById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        }
    }

    @Transactional
    public void deleteByIdIn(Collection<Long> ids) {
        for (Long id : ids) {
            deleteById(id);
        }
    }

    @Transactional
    public RatingDto getUserRatingById(Long userId, Long ratingId) {
        Rating found = findById(userId).getRates()
                .stream()
                .filter(rating -> ratingId.equals(rating.getId()))
                .findFirst()
                .orElseThrow(
                        () -> new EntityNotFoundException(String.format(RATING_WITH_ID_NOT_FOUND, ratingId)));
        return modelMapper.map(found, RatingDto.class);
    }
}
