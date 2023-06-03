package com.example.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO {
    private int size;
    private int page;

    private String type;
    private String keyword;

    public PageRequestDTO() {
        this.page=1;
        this.size=10;
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
