package com.example.mreview.dto;

import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
@Builder
@AllArgsConstructor
@ToString
public class PageRequestDTO{
    private int size;
    private int page;



    private String keyword;
    private String type;

    public PageRequestDTO(){
        this.size=10;
        this.page=1;
    }
    public void setPage(int page){
        if(page<1){
            this.page = 1;
        }
        else{
            this.page=page;
        }
    }

    public Pageable getPageable(Sort sort){
        return PageRequest.of(this.page-1,this.size,sort);
    }
}
