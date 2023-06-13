package com.example.club.security.service;

import com.example.club.entity.Note;
import com.example.club.repository.NoteRepository;
import com.example.club.security.dto.NoteDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;
    @Override
    public Long register(NoteDTO dto) {
        return noteRepository.save(dtoToEntity(dto)).getNum();
    }

    @Override
    public NoteDTO get(Long num) {
        Optional<Note> result = noteRepository.getWithWriter(num);

        return result.isPresent() ? entityToDTO(result.get()) : null;
    }

    @Override
    public void modify(NoteDTO dto) {
        Long num = dto.getNum();
        Optional<Note> result = noteRepository.findById(num);

        if(result.isPresent()){
            Note note = result.get();

            note.changeTitle(dto.getTitle());
            note.changeContent(dto.getContent());

            noteRepository.save(note);
        }
    }

    @Override
    public void remove(Long num) {
        noteRepository.deleteById(num);
    }

    @Override
    public List<NoteDTO> getAllWithWriter(String writerEmail) {
        List<Note> list = noteRepository.getList(writerEmail);

        return list.stream().map(this::entityToDTO).collect(Collectors.toList());
    }
}
