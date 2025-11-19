package com._37collaborationserver.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com._37collaborationserver.domain.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
