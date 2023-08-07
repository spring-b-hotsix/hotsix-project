package com.sparta.hotsixproject.side.repository;

import com.sparta.hotsixproject.side.entity.Side;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SideRepository extends JpaRepository<Side, Long> {
    Side findByName(String name);

    Optional<Side> findById(Long id);
}
