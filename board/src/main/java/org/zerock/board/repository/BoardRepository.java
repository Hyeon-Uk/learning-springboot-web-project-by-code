package org.zerock.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.board.entity.Board;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long> {

    @Query("select b, w from Board b left join b.writer w where b.bno = :bno")
    Object getBoardWithWriter(@Param("bno") Long bno);

    @Query("select b,r from Board b left join Reply r on r.board = b where b.bno = :bno")
    List<Object[]> getBoardWithReply(@Param("bno") Long bno);

    @Query(value="SELECT b,w,count(r) FROM Board b " +
            "LEFT JOIN b.writer w " +
            "LEFT JOIN Reply r ON r.board = b " +
            "GROUP BY b",
        countQuery = "SELECT COUNT(b) FROM Board b"
    )
    Page<Object[]> getBoardWithReplyCount(Pageable pageable);

    @Query("SELECT b,w,count(r) FROM Board b " +
            "LEFT JOIN b.writer w " +
            "LEFT JOIN Reply r on r.board = b " +
            "WHERE b.bno = :bno")
    Object getBoardByBno(@Param("bno") Long bno);
}
