package com.sparta.hotsixproject.side.repository;

import com.sparta.hotsixproject.side.entity.Side;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SideRepository extends JpaRepository<Side, Long>, SideRepositoryCustom {
}
