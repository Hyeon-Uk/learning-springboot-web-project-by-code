package org.zerock.board.service;

import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Member;

public interface BoardService {
    Long register(BoardDTO dto);

    PageResultDTO<BoardDTO,Object[]> getList(PageRequestDTO pageRequestDTO);

    BoardDTO get(Long bno);

    void removeWithReplies(Long bno);

    void modify(BoardDTO boardDTO);

    default Board dtoToEntity(BoardDTO dto){
        return Board.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .bno(dto.getBno())
                .writer(Member.builder()
                        .email(dto.getWriterEmail())
                        .build())
                .build();
    }

    default BoardDTO entityToDTO(Board board,Member member,Long replyCount){
        return BoardDTO.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .bno(board.getBno())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .replyCount(replyCount.intValue())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .build();
    }
}
