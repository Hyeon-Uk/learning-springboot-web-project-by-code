package com.example.mreview.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDTO<DTO,EN>{
    private List<DTO> dtoList;

    private int totalPage;
    private int page;
    private int size;

    private int start,end;
    private boolean next,prev;

    private List<Integer> pageList;

    public PageResultDTO(Page<EN>result, Function<EN,DTO> fn){
        dtoList = result.stream().map(fn).collect(Collectors.toList());

        this.totalPage = result.getTotalPages();
        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable) {
        this.page = pageable.getPageNumber()+1;
        this.size = pageable.getPageSize();

        int tempEnd = (int)(Math.ceil(this.page/(double)this.size)*this.size);

        this.start = tempEnd - (this.size -1);
        this.end = Math.min(this.totalPage,tempEnd);

        this.next = (this.end < this.totalPage);
        this.prev = (this.start > 1);

        pageList = IntStream.rangeClosed(this.start,this.end).boxed().collect(Collectors.toList());
    }
}
