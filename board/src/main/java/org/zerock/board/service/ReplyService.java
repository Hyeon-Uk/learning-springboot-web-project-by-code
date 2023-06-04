package org.zerock.board.service;

import org.zerock.board.dto.ReplyDTO;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Reply;

import java.util.List;

public interface ReplyService {
    Long register(ReplyDTO dto);

    List<ReplyDTO> getList(Long bno);

    void modify(ReplyDTO dto);

    void remove(Long rno);

    default Reply dtoToEntity(ReplyDTO dto){
        Board board = Board.builder()
                .bno(dto.getBno())
                .build();

        return Reply.builder()
                .rno(dto.getRno())
                .text(dto.getText())
                .replyer(dto.getReplyer())
                .board(board)
                .build();
    }

    default ReplyDTO entityToDTO(Reply reply){
        return ReplyDTO.builder()
                .rno(reply.getRno())
                .text(reply.getText())
                .replyer(reply.getReplyer())
                .regDate(reply.getRegDate())
                .modDate(reply.getModDate())
                .build();
    }
}
