package com.sparta.hotsixproject.board.service;

import com.sparta.hotsixproject.board.dto.BoardRequestDto;
import com.sparta.hotsixproject.board.dto.BoardResponseDto;
import com.sparta.hotsixproject.board.dto.MemberResponseDto;
import com.sparta.hotsixproject.board.entity.Board;
import com.sparta.hotsixproject.boarduser.entity.BoardUser;
import com.sparta.hotsixproject.board.repository.BoardRepository;
import com.sparta.hotsixproject.boarduser.repository.BoardUserRepository;
import com.sparta.hotsixproject.common.advice.ApiResponseDto;
import com.sparta.hotsixproject.user.entity.User;
import com.sparta.hotsixproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final BoardUserRepository boardUserRepository;

    //메인화면 - 로그인한 사용자가 생성한 보드 불러오는 메서드
    @Override
    public List<BoardResponseDto> getMyBoards(User user) {
        User loginedUser = findUser(user.getId());
        //본인이 생성한 보드 불러오기
        return boardRepository.findAllByUser(loginedUser).stream().map(BoardResponseDto::new).toList();
    }

    //메인화면 - 로그인한 사용자가 참여한 보드를 불러오는 메서드
    @Override
    public List<BoardResponseDto> getGuestBoards(User user) {
        User loginedUser = findUser(user.getId());
        //본인이 참여한 보드 불러오기
        return findGuestBoards(loginedUser)
                .stream()
                .filter(board->!board.getUser().equals(loginedUser)) //본인이 생성한 보드는 제외
                .map(BoardResponseDto::new)
                .toList();
    }


    //선택한 보드정보를 가져오는 메서드
    @Override
    public BoardResponseDto getBoard(Long id, User user) {
        User loginedUser = findUser(user.getId());
        Board targetBoard = findBoard(id);

        //보드에 속한 유저인지 확인
        checkBoardMember(loginedUser,targetBoard);

        //선택한 보드 불러오기
        return new BoardResponseDto(findBoard(id));
    }

    //새 보드 생성 메서드
    @Override
    public ApiResponseDto createBoard(BoardRequestDto requestDto, User user) {
        User loginedUser = findUser(user.getId());
        Color color = new Color(requestDto.getRed(), requestDto.getGreen(), requestDto.getBlue());
        Board board = new Board(requestDto.getName(),requestDto.getDescription(),loginedUser,color);

        //새 보드 추가
        boardRepository.save(board);

        //보드-유저 관계 테이블에 추가
        BoardUser boardUser = new BoardUser(loginedUser,board);
        boardUserRepository.save(boardUser);

        return new ApiResponseDto("새로운 보드를 생성했습니다.", 201);
    }

    //보드 설정 변경(제목, 내용, 색상) 메서드
    @Override
    public ApiResponseDto updateBoard(Long id, BoardRequestDto requestDto,User user) {
        User loginedUser = findUser(user.getId());
        Board targetBoard = findBoard(id);
        //보드 멤버인지 확인
        checkBoardMember(loginedUser,targetBoard);

        //보드 멤버일 경우 보드 수정 가능하도록 허용
        targetBoard.update(requestDto);
        return new ApiResponseDto("보드 수정이 완료되었습니다.",200);
    }

    //보드 삭제 메서드
    @Override
    public ApiResponseDto deleteBoard(Long id, User user) {
        User loginedUser = findUser(user.getId());
        Board targetBoard = findBoard(id);

        //보드를 생성하지 않은 유저가 삭제 시도할 때
        if(!targetBoard.getUser().equals(loginedUser)) {
            throw new IllegalArgumentException("보드 작성자만 삭제할 수 있습니다.");
        }
        boardRepository.delete(targetBoard);
        return new ApiResponseDto("보드가 삭제되었습니다.",200);
    }

    //보드 초대 메서드
    @Override
    public ApiResponseDto inviteBoard(Long id, String email, User user) {
        User loginedUser = findUser(user.getId());
        Board targetBoard = findBoard(id);
        //로그인유저가 보드에 속한 유저인지 확인
        checkBoardMember(loginedUser,targetBoard);

        //초대할 유저 객체 불러오기
        User inviteUser = userRepository.findByEmail(email).orElse(null);
        if(inviteUser==null){
            throw new IllegalArgumentException("입력한 이메일로 가입한 사용자가 존재하지 않습니다.");
        }
        //초대할 유저가 이미 멤버에 속했는지 확인
        if(boardUserRepository.findByUserAndBoard(inviteUser,targetBoard).orElse(null)!=null){
            throw new IllegalArgumentException("해당 유저는 이미 보드의 멤버입니다.");
        }

        //보드-유저 관계 테이블에 추가
        BoardUser boardUser = new BoardUser(inviteUser,targetBoard);
        boardUserRepository.save(boardUser);
        return new ApiResponseDto(inviteUser.getNickname()+"님을 보드 멤버에 추가했습니다.",201);
    }

    // 보드 멤버 전체 조회
    @Override
    public List<MemberResponseDto> getMembers(Long boardId) {
        return boardUserRepository.findByBoard_Id(boardId).stream().map(MemberResponseDto::new).toList();
    }


    // --private methods--

    private User findUser(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 사용자 입니다.")
        );
    }
    private List<Board> findGuestBoards(User user){
       return boardUserRepository.findAllByUser(user).stream()
                .map(BoardUser::getBoard)//Board_User연관테이블에서 로그인된 사용자의 board만 가져와서 리스트 만들기
                .collect(Collectors.toList());
    }

    public Board findBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 보드 입니다.")
        );
    }
    public void checkBoardMember(User user, Board board) {
        boardUserRepository.findByUserAndBoard(user, board).orElseThrow(() ->
                new IllegalArgumentException("해당 보드 권한을 가진 유저가 아닙니다.")
        );
    }
}
