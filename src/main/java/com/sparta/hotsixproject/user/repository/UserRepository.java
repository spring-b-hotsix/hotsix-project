package com.sparta.hotsixproject.user.repository;

import com.sparta.hotsixproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByNickname(String nickname);
    // Optional<User> findByNickName(String nickname);
    Optional<User> findByEmail(String email);
}
