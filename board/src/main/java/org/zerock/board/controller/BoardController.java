package org.zerock.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;
import org.zerock.board.service.BoardService;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        model.addAttribute("result",boardService.getList(pageRequestDTO));
    }

    @GetMapping("/register")
    public void register(PageRequestDTO pageRequestDTO){

    }

    @PostMapping("/register")
    public String registerPost(BoardDTO dto, RedirectAttributes redirectAttributes){
        Long bno = boardService.register(dto);

        redirectAttributes.addAttribute("msg",bno);

        return "redirect:/board/list";
    }

    @GetMapping({"/read","/modify"})
    public void read(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO,Long bno,Model model){
        BoardDTO boardDTO = boardService.get(bno);
        model.addAttribute("dto",boardDTO);
    }

    @PostMapping("/remove")
    public String remove(long bno,RedirectAttributes redirectAttributes){
        boardService.removeWithReplies(bno);

        redirectAttributes.addAttribute("msg",bno);

        return "redirect:/board/list";
    }

    @PostMapping("/modify")
    public String modifyPost(BoardDTO dto,
                             @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO,
                             RedirectAttributes redirectAttributes){
        log.info("post modify.................................");
        log.info("dto : {}",dto);

        boardService.modify(dto);

        redirectAttributes.addAttribute("page",pageRequestDTO.getPage());
        redirectAttributes.addAttribute("type",pageRequestDTO.getType());
        redirectAttributes.addAttribute("keyword",pageRequestDTO.getKeyword());

        redirectAttributes.addAttribute("bno",dto.getBno());

        return "redirect:/board/read";

    }
}
