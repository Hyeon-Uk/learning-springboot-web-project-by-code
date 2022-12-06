package org.zerock.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.zerock.board.dto.ReplyDTO;
import org.zerock.board.entity.Board;
import org.zerock.board.repository.ReplyRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReplyServiceImpl implements ReplyService{
    private final ReplyRepository replyRepository;

    @Override
    public Long register(ReplyDTO dto) {
        return replyRepository.save(dtoToEntity(dto)).getRno();
    }

    @Override
    public List<ReplyDTO> getList(Long bno) {
        return replyRepository.getRepliesByBoardOrderByRno(
                Board.builder()
                        .bno(bno)
                        .build()
        ).stream().map(en->entityToDTO(en)).collect(Collectors.toList());
    }

    @Override
    public void modify(ReplyDTO dto) {
        replyRepository.save(dtoToEntity(dto));
    }

    @Override
    public void remove(Long rno) {
        replyRepository.deleteById(rno);
    }
}
