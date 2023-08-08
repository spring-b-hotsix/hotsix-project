package com.sparta.hotsixproject.side.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SideRepositoryImpl implements SideRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
}
