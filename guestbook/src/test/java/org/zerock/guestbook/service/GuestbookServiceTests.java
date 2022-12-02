package org.zerock.guestbook.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.dto.PageResultDTO;
import org.zerock.guestbook.entity.Guestbook;

import java.util.List;

@SpringBootTest
public class GuestbookServiceTests {
    @Autowired
    private GuestbookService guestbookService;

    @Test
    public void testRegister(){
        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .title("Sample Title")
                .content("Sample Content")
                .writer("user0")
                .build();

        System.out.println(guestbookService.register(guestbookDTO));
    }

    @Test
    public void testList(){
        PageRequestDTO dto = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();

        PageResultDTO<GuestbookDTO, Guestbook> result = guestbookService.getList(dto);

        List<GuestbookDTO> dtoList = result.getDtoList();
        dtoList.stream().forEach(System.out::println);
    }

    @Test
    public void testList1(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .type("tc")
                .keyword("한글")
                .build();

        PageResultDTO<GuestbookDTO, Guestbook> result = guestbookService.getList(pageRequestDTO);

        System.out.println("result.isPrev() = " + result.isPrev());
        System.out.println("result.isNext() = " + result.isNext());
        System.out.println("result.getTotalPage() = " + result.getTotalPage());
        System.out.println("result.getSize() = " + result.getSize());

        result.getPageList().forEach(System.out::println);
        System.out.println("==============================================");
        result.getDtoList().stream().forEach(System.out::println);
    }
}
