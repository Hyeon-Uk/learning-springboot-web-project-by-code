package org.zerock.board.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardServiceTest {
    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister(){
        BoardDTO dto = BoardDTO.builder()
                .title("Test...")
                .content("Test....")
                .writerEmail("user55@aaa.com")
                .build();

        Long bno = boardService.register(dto);

        System.out.println("bno = " + bno);
    }
    @Test
    public void testList(){
        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        PageResultDTO<BoardDTO, Object[]> result = boardService.getList(pageRequestDTO);

        for(BoardDTO dto : result.getDtoList()){
            System.out.println(dto);
        }
    }

    @Test
    public void testGet(){
        Long bno = 100l;

        BoardDTO boardDTO = boardService.get(bno);
        System.out.println(boardDTO);
    }

    @Test
    public void testRemove(){
        Long bno = 1l;
        boardService.removeWithReplies(bno);
    }

    @Test
    @Transactional
    public void testModify(){
        BoardDTO dto = BoardDTO.builder()
                .bno(2l)
                .title("변경된 제목")
                .content("변경된 타이틀")
                .build();

        boardService.modify(dto);

        BoardDTO result = boardService.get(2l);

        assertThat(result.getTitle()).isEqualTo(dto.getTitle());
        assertThat(result.getBno()).isEqualTo(dto.getBno());
        assertThat(result.getContent()).isEqualTo(dto.getContent());
    }
}