package com.example.club.security.service;

import com.example.club.entity.ClubMember;
import com.example.club.entity.Note;
import com.example.club.security.dto.NoteDTO;

import java.util.List;

public interface NoteService {
    Long register(NoteDTO dto);

    NoteDTO get(Long num);

    void modify(NoteDTO dto);

    void remove(Long num);

    List<NoteDTO> getAllWithWriter(String writerEmail);

    default Note dtoToEntity(NoteDTO dto){
        return Note.builder()
                .num(dto.getNum())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(ClubMember.builder().email(dto.getWriterEmail()).build())
                .build();
    }

    default NoteDTO entityToDTO(Note note){
        return NoteDTO.builder()
                .num(note.getNum())
                .title(note.getTitle())
                .content(note.getContent())
                .writerEmail(note.getWriter().getEmail())
                .regDate(note.getRegDate())
                .modDate(note.getModDate())
                .build();
    }
}
