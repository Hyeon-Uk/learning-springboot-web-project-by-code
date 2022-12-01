package org.zerock.guestbook.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.dto.PageResultDTO;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.repository.GuestbookRepository;

import java.util.Optional;
import java.util.function.Function;

@Service
@Slf4j
@RequiredArgsConstructor
public class GuestbookServiceImpl implements GuestbookService{
    private final GuestbookRepository guestbookRepository;

    @Override
    public Long register(GuestbookDTO dto) {
        Guestbook entity = dtoToEntity(dto);

        guestbookRepository.save(entity);

        return entity.getGno();
    }

    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO dto) {
        Pageable pageable = dto.getPageable(Sort.by("gno").descending());

        Page<Guestbook> result = guestbookRepository.findAll(pageable);

        Function<Guestbook,GuestbookDTO> fn = entity -> entityToDto(entity);

        return new PageResultDTO<>(result,fn);
    }

    @Override
    public GuestbookDTO read(Long gno) {
        Optional<Guestbook> result = guestbookRepository.findById(gno);
        return result.isPresent() ? entityToDto(result.get()) : null;
    }
}
