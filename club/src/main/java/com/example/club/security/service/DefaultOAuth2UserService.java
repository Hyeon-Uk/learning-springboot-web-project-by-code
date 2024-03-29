package com.example.club.security.service;

import com.example.club.entity.ClubMember;
import com.example.club.entity.ClubMemberRole;
import com.example.club.repository.ClubMemberRepository;
import com.example.club.security.dto.ClubAuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class DefaultOAuth2UserService extends org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService {
    private final ClubMemberRepository clubMemberRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        log.info("----------------------");
        log.info("userRequest = "+userRequest);

        String clientName = userRequest.getClientRegistration().getClientName();
        log.info("clientName = {}",clientName);
        log.info(userRequest.getAdditionalParameters());

        OAuth2User oAuth2User = super.loadUser(userRequest);

        log.info("==============");
        oAuth2User.getAttributes().forEach((k,v)->{
            log.info(k+" : "+v);
        });

        String email = null;

        if(clientName.equals("Google")){
            email = oAuth2User.getAttribute("email");
        }

        log.info("email : {}",email);

//        ClubMember member = saveSocialMember(email);

//        return oAuth2User;

        ClubMember member = saveSocialMember(email);

        ClubAuthMemberDTO clubAuthMember = new ClubAuthMemberDTO(
                member.getEmail(),
                member.getPassword(),
                true,
                member.getRoleSet(),
                oAuth2User.getAttributes()
        );
        clubAuthMember.setName(member.getName());

        return clubAuthMember;
    }

    private ClubMember saveSocialMember(String email){
        //기존에 동일한 이메일로 가입한 회원이 있는 경우에는 그대로 조회만
        Optional<ClubMember> result = clubMemberRepository.findByEmail(email, true);

        if(result.isPresent()){
            return result.get();
        }

        ClubMember clubMember = ClubMember.builder()
                .email(email)
                .password(passwordEncoder.encode("1111"))
                .fromSocial(true)
                .build();

        clubMember.addMemberRole(ClubMemberRole.USER);

        clubMemberRepository.save(clubMember);

        return clubMember;
    }
}
