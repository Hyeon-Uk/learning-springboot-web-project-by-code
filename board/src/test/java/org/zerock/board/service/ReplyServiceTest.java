package org.zerock.board.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board.dto.ReplyDTO;

import java.util.List;

@SpringBootTest
public class ReplyServiceTest {
    @Autowired
    private ReplyService replyService;

    @Test
    public void testGetList(){
        Long bno = 100l;
        List<ReplyDTO> replyDTOList = replyService.getList(bno);

        replyDTOList.stream().forEach(System.out::println);
    }
}
