package com.sparta.hotsixproject.carduser.repository;

import com.sparta.hotsixproject.carduser.entity.CardUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardUserRepository extends JpaRepository<CardUser, Long> {
    Optional<CardUser> findByCard_IdAndUser_Email(Long id, String email);
}
