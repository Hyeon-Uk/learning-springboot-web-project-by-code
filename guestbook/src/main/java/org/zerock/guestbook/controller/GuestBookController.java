package org.zerock.guestbook.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/guestbook")
@Slf4j
@RequiredArgsConstructor
public class GuestBookController {
    @GetMapping({"/","/list"})
    public String list(){
        log.info("list...");

        return "/guestbook/list";
    }
}
