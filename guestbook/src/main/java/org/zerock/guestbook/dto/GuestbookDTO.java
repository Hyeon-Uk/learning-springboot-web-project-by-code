package org.zerock.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GuestbookDTO {
    private Long gno;
    private String title;
    private String content;
    private String writer;
    @DateTimeFormat(pattern="yyyy/MM/dd hh:mm:ss")
    private LocalDateTime regDate,modDate;
}
