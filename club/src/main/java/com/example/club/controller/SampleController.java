package com.example.club.controller;

import com.example.club.security.dto.ClubAuthMemberDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequestMapping("/sample/")
public class SampleController {
    @GetMapping("/all")
    @PreAuthorize("permitAll()")
    public void exAll(){
        log.info("exAll........");
    }

    @GetMapping("/member")
    @PreAuthorize("isAuthenticated()")
    public void exMember(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMember){
        log.info("exMember........");

        log.info("---------------------------------");
        log.info(clubAuthMember);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public void exAdmin(){
        log.info("exAdmin........");
    }

    @PreAuthorize("#clubAuthMember != null && #clubAuthMember.username eq \"user95@zerock.org\"")
    @GetMapping("/exOnly")
    public String exMemberOnly(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMember){
        log.info("exMemberOnly....");
        log.info(clubAuthMember);

        return "/sample/admin";
    }
}
