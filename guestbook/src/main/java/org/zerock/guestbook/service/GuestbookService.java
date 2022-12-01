package org.zerock.guestbook.service;

import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.dto.PageResultDTO;
import org.zerock.guestbook.entity.Guestbook;

public interface GuestbookService {
    Long register(GuestbookDTO guestbookDTO);

    PageResultDTO<GuestbookDTO,Guestbook> getList(PageRequestDTO dto);

    GuestbookDTO read(Long gno);

    default Guestbook dtoToEntity(GuestbookDTO dto){
        return Guestbook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
    }

    default GuestbookDTO entityToDto(Guestbook guestbook){
        return GuestbookDTO.builder()
                .gno(guestbook.getGno())
                .title(guestbook.getTitle())
                .content(guestbook.getContent())
                .writer(guestbook.getWriter())
                .regDate(guestbook.getRegDate())
                .modDate(guestbook.getModDate())
                .build();
    }
}
