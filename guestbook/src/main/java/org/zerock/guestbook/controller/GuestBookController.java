package org.zerock.guestbook.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.service.GuestbookService;

import javax.websocket.server.PathParam;

@Controller
@RequestMapping("/guestbook")
@Slf4j
@RequiredArgsConstructor
public class GuestBookController {
    private final GuestbookService guestbookService;
    @GetMapping({"/","/list"})
    public String list(PageRequestDTO pageRequestDTO, Model model){
        model.addAttribute("result",guestbookService.getList(pageRequestDTO));
        return "/guestbook/list";
    }

    @GetMapping("/register")
    public void register(Model model){
        log.info("Register...");
        model.addAttribute("guestbookDTO",new GuestbookDTO());
    }

    @PostMapping("/register")
    public String registerPost(GuestbookDTO dto, RedirectAttributes redirectAttributes){
        log.info("dto..."+dto);

        Long gno = guestbookService.register(dto);

        redirectAttributes.addFlashAttribute("msg",gno);

        return "redirect:/guestbook/list";
    }

    @GetMapping({"/read","/modify"})
    public void read(Long gno,
                       @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                       Model model){
        log.info("gno : "+gno);

        GuestbookDTO dto = guestbookService.read(gno);
        model.addAttribute("dto",dto);
    }

    @PostMapping("/modify")
    public String modifyPost(GuestbookDTO dto,
                             @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                             RedirectAttributes redirectAttributes){

        log.info("dto = {}",dto);
        log.info("requestDTO = {}",requestDTO);

        guestbookService.modify(dto);

        redirectAttributes.addAttribute("page",requestDTO.getPage());
        redirectAttributes.addAttribute("type",requestDTO.getType());
        redirectAttributes.addAttribute("keyword",requestDTO.getKeyword());
        redirectAttributes.addAttribute("size",requestDTO.getSize());
        redirectAttributes.addAttribute("gno",dto.getGno());
        return "redirect:/guestbook/read";
    }

    @PostMapping("/remove")
    public String remove(long gno,RedirectAttributes redirectAttributes){
        log.info("gno = {}",gno);

        guestbookService.remove(gno);

        redirectAttributes.addFlashAttribute("msg",gno);

        return "redirect:/guestbook/list";
    }
}
