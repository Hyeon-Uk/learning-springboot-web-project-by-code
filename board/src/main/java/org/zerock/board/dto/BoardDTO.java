package org.zerock.board.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDTO {
    private Long bno;
    private String title;
    private String content;
    private String writerEmail;
    private String writerName;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    private int replyCount;
}
