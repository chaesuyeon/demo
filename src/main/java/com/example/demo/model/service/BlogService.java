package com.example.demo.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.model.domain.Board;
import com.example.demo.model.repository.BlogRepository;
import com.example.demo.model.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlogService {

    // Article용 (지금은 거의 안 씀)
    private final BlogRepository blogRepository;

    // Board용
    private final BoardRepository boardRepository;

    // 전체 목록
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    // 저장
    public Board save(AddArticleRequest request){
        return boardRepository.save(request.toEntity());
    }

    // 페이징 목록
    public Page<Board> findAll(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    // 검색
    public Page<Board> searchByKeyword(String keyword, Pageable pageable) {
        return boardRepository.findByTitleContainingIgnoreCase(keyword, pageable);
    }

    // 단일 조회
    public Optional<Board> findById(Long id) {
        return boardRepository.findById(id);
    }

    // 수정
    public void update(Long id, AddArticleRequest request) {

        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        // Board 엔티티는 setter가 없고 update()만 있음
        board.update(
                request.getTitle(),
                request.getContent(),
                board.getUser(),       // 기존 값 유지
                board.getNewdate(),
                board.getCount(),
                board.getLikec()
        );

        boardRepository.save(board);
    }

    // 삭제
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }
}
