package com.example.club.security.filter;

import com.example.club.security.dto.ClubAuthMemberDTO;
import com.example.club.security.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;

@Log4j2
public class ApiLoginFilter extends AbstractAuthenticationProcessingFilter {
    private JWTUtil jwtUtil;
    public ApiLoginFilter(String defaultFilterProcessUrl,JWTUtil jwtUtil){
        super(defaultFilterProcessUrl);
        this.jwtUtil = jwtUtil;
    }



    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        log.info("---------------Api Login Filter ---------------");
        log.info("attemptAuthentication");

        String email = request.getParameter("email");
        String pw = request.getParameter("pw");

        System.out.println(email);
        System.out.println(pw);

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(email,pw);

        Authentication authenticate = getAuthenticationManager().authenticate(authToken);
        System.out.println("Authenticate = "+authenticate);

        return authenticate;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("------------------------API Login Filter Success Handler --------------------------");
        log.info("successfulAuthentication : {}",authResult);

        log.info(authResult.getPrincipal());

        String email = ((ClubAuthMemberDTO)authResult.getPrincipal()).getEmail();

        String token = null;
        try{
            token = jwtUtil.generateToken(email);

            response.setContentType("text/plain");
            response.getOutputStream().write(token.getBytes());

            log.info(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
