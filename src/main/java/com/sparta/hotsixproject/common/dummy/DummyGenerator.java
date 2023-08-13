package com.sparta.hotsixproject.common.dummy;

import com.github.javafaker.Faker;
import com.sparta.hotsixproject.board.repository.BoardRepository;
import com.sparta.hotsixproject.card.repository.CardRepository;
import com.sparta.hotsixproject.label.repository.LabelRepository;
import com.sparta.hotsixproject.side.repository.SideRepository;
import com.sparta.hotsixproject.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class DummyGenerator implements CommandLineRunner {
    private final Faker faker;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final SideRepository sideRepository;
    private final CardRepository cardRepository;
    private final LabelRepository labelRepository;

    @Autowired
    public DummyGenerator(
            UserRepository userRepository, BoardRepository boardRepository,
            SideRepository sideRepository, CardRepository cardRepository,
            LabelRepository labelRepository
    ) {
        this.faker = new Faker(new Locale("ko"));
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
        this.sideRepository = sideRepository;
        this.cardRepository = cardRepository;
        this.labelRepository = labelRepository;
    }

    // 실행
    @Override
    public void run(String... args) throws Exception {

    }

    // User


    // Board

    // Side(컬럼)

    // Card

    // Card 세부 (체크리스트, 작업자 등)
}
