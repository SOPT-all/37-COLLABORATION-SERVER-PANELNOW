package com._37collaborationserver.domain.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com._37collaborationserver.domain.user.dto.UserInfoResponse;
import com._37collaborationserver.domain.user.entity.User;
import com._37collaborationserver.domain.user.repository.UserRepository;
import com._37collaborationserver.global.exception.NotFoundException;
import com._37collaborationserver.global.exception.code.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;

	public User getUserById(final Long userId){
		return userRepository.findById(userId)
			.orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
	}

	public UserInfoResponse getUserInfo(final Long userId){
		User user = getUserById(userId);
		return UserInfoResponse.from(user);
	}
}
