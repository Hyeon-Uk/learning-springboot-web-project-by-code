package com.example.club.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoteDTO {
    private Long num;
    private String title;
    private String content;
    private String writerEmail;
    private LocalDateTime regDate,modDate;
}
